package com.example.productservice.service;

import com.example.productservice.repository.ProductRepository;
import com.example.productservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    //importing ProductRepository
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
         this.productRepository= productRepository;
    }

//    private Long idCounter = 10L;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id)
    {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product) {
//        product.setId(idCounter++);
        productRepository.save(product);
        return product;
    }
}