package com.solomko.service;

import com.solomko.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Map<String, Double> getAllProducts(){
        Map<String, Double> products = productRepository.getProducts();
        return products;
    }
}
