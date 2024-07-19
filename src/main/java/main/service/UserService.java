/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import main.api.model.RegistrationBody;
import main.exception.UserAlreadyExistsException;
import main.models.LocalUser;
import main.repo.LocalUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class UserService {
    @Autowired
    public UserService(LocalUserRepo repo) {
        this.repo = repo;
    }
    private final LocalUserRepo repo;
    
    
    @Transactional
    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException{
        if(repo.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent() 
                || repo.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()){
            throw new UserAlreadyExistsException("User Already Exists");
        }
        var user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstname());
        user.setLastName(registrationBody.getLastname());
        user.setPassword(registrationBody.getPassword());
        user.setUsername(registrationBody.getUsername());
        return repo.save(user);
    }
}
