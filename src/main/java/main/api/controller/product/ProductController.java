/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.api.controller.product;

import java.util.List;
import main.models.Product;
import main.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }
    private final ProductService service;
    
    @GetMapping
    public List<Product> getProducts(){
        return service.getProducts();
    }
}
