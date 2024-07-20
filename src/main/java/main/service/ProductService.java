/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import java.util.List;
import main.models.Product;
import main.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class ProductService {
    @Autowired
    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }
    private final ProductRepo repo;
    
    public List<Product> getProducts(){
        return repo.findAll();
    }
}
