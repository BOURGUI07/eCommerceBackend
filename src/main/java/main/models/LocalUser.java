/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author hp
 */
@Table(name="localuser")
@Entity
@Data
public class LocalUser {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id", nullable=false)
    private Integer id;
    @Column(name="username")
    private String username;
    @Column(name="pass_word")
    private String password;
    @Column(name="email")
    private String email;
    @Column(name="firstname")
    private String firstName;
    @Column(name="lastname")
    private String lastName;
    @OneToMany(mappedBy="user", cascade=CascadeType.REMOVE, orphanRemoval=true)
    @JsonManagedReference
    private List<Address> addresses = new ArrayList<>();
    
    @OneToMany(mappedBy="user", cascade=CascadeType.REMOVE, orphanRemoval=true)
    @JsonManagedReference
    private List<WebOrder> orders = new ArrayList<>();
    
    
    
}
