/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.api.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.api.model.LoginBody;
import main.api.model.LoginResponse;
import main.api.model.RegistrationBody;
import main.exception.EmailFailureException;
import main.exception.UserAlreadyExistsException;
import main.exception.UserNotVerifiedException;
import main.models.LocalUser;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    public AuthenticationController(UserService service) {
        this.service = service;
    }
    private final UserService service;
    @Operation(summary="Register a User")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="User Created Sucessfully"),
        @ApiResponse(responseCode="409", description="User Already Exists"),
        @ApiResponse(responseCode="500", description="Email Failure")
    })
    @PostMapping("/register")
    public ResponseEntity<LocalUser> registerUser(@Valid @RequestBody RegistrationBody registrationBody){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registerUser(registrationBody));
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }catch(EmailFailureException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/login")
    @Operation(summary="Login a User")
    @ApiResponses(value={
        @ApiResponse(responseCode="200", description="User Logged In Sucessfully"),
        @ApiResponse(responseCode="400", description="User Token is Null"),
        @ApiResponse(responseCode="500", description="Email Failure"),
        @ApiResponse(responseCode="403", description="User No Verified")
    })
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody body) {
        String jwt=null;
        try{
             jwt= service.loginUser(body);
        }catch(UserNotVerifiedException e){
            var loginResponse = new LoginResponse();
            loginResponse.setSucecss(false);
            String reason = "USER_NOT_VERIFIED";
            if(e.isEmailSent()){
                reason+="_EMAIL_RESENT";
            }
            loginResponse.setFailureReason(reason);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(loginResponse);
        }catch(EmailFailureException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LoginResponse response = new LoginResponse();
        response.setJwt(jwt);
        response.setSucecss(true);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/me")
    public LocalUser getLoggedInUserProfile(@AuthenticationPrincipal LocalUser user){
        return user;
    }
    
    @PostMapping("/verify")
    @Operation(summary="Verify a User Email")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="User Email Sucessfully Verified"),
        @ApiResponse(responseCode="409", description="User Already Exists")
    })
    public ResponseEntity verifyEmail(String token){
        if(service.verifyUser(token)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
