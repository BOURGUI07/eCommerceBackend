/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Entity
@Table(name="product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="product_id", nullable=false)
    private Integer id;
    @Column(name="productname")
    private String name;
    @Column(name="shortdesc")
    private String shortDesc;
    @Column(name="longdesc")
    private String longDesc;
    @Column(name="price")
    private Double price;
    
    @OneToMany(mappedBy="product")
    @JsonManagedReference
    private List<OrderDetail> details = new ArrayList<>();
    
}
