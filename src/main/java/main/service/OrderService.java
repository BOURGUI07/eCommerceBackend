/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import java.util.List;
import main.models.LocalUser;
import main.models.WebOrder;
import main.repo.WebOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class OrderService {
    @Autowired
    public OrderService(WebOrderRepo repo) {
        this.repo = repo;
    }
    private final WebOrderRepo repo;
    
    public List<WebOrder> getOrders(LocalUser user){
        return repo.findByUser(user);
    }
}
