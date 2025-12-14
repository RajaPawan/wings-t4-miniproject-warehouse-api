package com.example.warehouse.service;

import com.example.warehouse.model.Product;
import com.example.warehouse.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    /*
   Implement the business logic for the ProductService  operations in this class
   Make sure to add required annotations
    */

    @Autowired
    private ProductRepository productRepository;


    //to post all the Product details
    //created->201
    //badRequest->400
    public ResponseEntity<Object> postProduct(Product product) {
        try {
            Product saved = productRepository.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("SKU must be unique");
        }
    }
    //to get all the Product details
    //ok->200
    //badRequest->400
    public ResponseEntity<Object> getProduct() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }
    //to get the product with the value(pathVariable)
    //ok()->200
    //badRequest()->400
    public ResponseEntity<Object> getSimilarVendor(String value) {
        List<Product> products = productRepository.findByVendor(value);
        return ResponseEntity.ok(products);
    }


    //to update the Product with id as pathVariable and Product as object in RequestBody
    //ok->200
    //badRequest->400
    public ResponseEntity<Object> updateProduct(int id, Product product) {
        Optional<Product> optional = productRepository.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.badRequest().body("Product not found");
        }

        Product existing = optional.get();
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setVendor(product.getVendor());
        existing.setPrice(product.getPrice());
        existing.setStock(product.getStock());
        existing.setCurrency(product.getCurrency());
        existing.setImage_url(product.getImage_url());
        existing.setSku(product.getSku());

        return ResponseEntity.ok(productRepository.save(existing));
    }
    // to delete the product by using id as PathVariable
    //ok->200
    //badRequest->400
    public ResponseEntity<Object> deleteProductById(int id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Product not found");
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok("Deleted successfully");
    }


}
