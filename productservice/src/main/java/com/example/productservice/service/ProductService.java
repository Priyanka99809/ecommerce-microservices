package com.example.productservice.service;

import com.example.productservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();
    private Long idCounter = 10L;

    public List<Product> getAllProducts() {
        return products;
    }

    public Product createProduct(Product product) {
        product.setId(idCounter++);
        products.add(product);
        return product;
    }
}