/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.api.model;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author hp
 */
@Setter
@Getter
public class LoginResponse {
    private String jwt;
    private boolean sucecss;
    private String failureReason; 
}
