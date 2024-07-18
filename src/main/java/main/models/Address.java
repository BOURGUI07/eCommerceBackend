/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author hp
 */
@Entity
@Table(name="address")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="address_id", nullable=false)
    private Integer id;
    @Column(name="address1")
    private String addressLine1;
    @Column(name="address1")
    private String addressLine2;
    @Column(name="city")
    private String city;
    @Column(name="country")
    private String country;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="user_id")
    private LocalUser user;

    
    @OneToMany(mappedBy="address", cascade=CascadeType.REMOVE, orphanRemoval=true)
    @JsonManagedReference
    private List<WebOrder> orders = new ArrayList<>();
}
