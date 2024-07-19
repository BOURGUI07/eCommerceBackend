/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.api.controller.auth;

import jakarta.validation.Valid;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.api.model.LoginBody;
import main.api.model.LoginResponse;
import main.api.model.RegistrationBody;
import main.exception.UserAlreadyExistsException;
import main.models.LocalUser;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/register")
    public ResponseEntity<LocalUser> registerUser(@Valid @RequestBody RegistrationBody registrationBody){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registerUser(registrationBody));
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody body) {
        String jwt = service.loginUser(body);
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LoginResponse response = new LoginResponse();
        response.setJwt(jwt);
        return ResponseEntity.ok(response);
    }
}
