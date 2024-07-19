/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 *
 * @author hp
 */
@Getter
public class RegistrationBody {
    @NotBlank
    @Size(min=3, max=50, message="username must be between 3 and 50 characters")
    private String username;
    @Email
    private String email;
    @NotBlank
    @Size(min=8, max=50)
    @Pattern(regexp= "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message= "password must be at least 8 characters, one lowercase letter, one uppercase letter, one number, and one special character")
    private String password;
    @NotBlank
    @Size(min=3, max=50, message="firstname must be between 3 and 50 characters")
    private String firstname;
    @NotBlank
    @Size(min=3, max=50, message="lastname must be between 3 and 50 characters")
    private String lastname;
}
