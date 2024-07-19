/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class EncryptionService {
    @Value("${encryption.salt.rounds}")
    private int saltRounds;
    private String salt;
    
    @PostConstruct
    public void postConstruct(){
        salt = BCrypt.gensalt(saltRounds);
    }
    
    public String encyptPassword(String password){
        return BCrypt.hashpw(password, salt);
    }
    
    public boolean verifyPassword(String password, String hash){
        return BCrypt.checkpw(password, hash);
    }
}
