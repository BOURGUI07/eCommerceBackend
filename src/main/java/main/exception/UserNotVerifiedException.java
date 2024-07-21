/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.exception;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author hp
 */
@Setter
@Getter
public class UserNotVerifiedException extends Exception{

    public UserNotVerifiedException(boolean emailSent) {
        this.emailSent = emailSent;
    }
    private boolean emailSent;
}
