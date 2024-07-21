/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.Data;

/**
 *
 * @author hp
 */
@Entity
@Table(name="verification_token")
@Data
public class VerificationToken {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="verification_id", nullable=false)
    private Integer id;
    
    @Lob
    @Column(name="token", unique=true, nullable=false)
    private String token;
    
    @Column(name="created_timestamp", nullable=false)
    private Timestamp createdTimestamp;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="user_id", nullable=false)
    @JsonBackReference
    private LocalUser user;
}
