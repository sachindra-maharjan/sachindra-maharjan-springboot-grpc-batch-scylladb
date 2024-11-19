package org.sm.controller;

import java.util.List;

import org.sm.dto.Product;
import org.sm.service.ProductServiceRPC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    
    private final ProductServiceRPC productServiceRPC;

    public ProductController(ProductServiceRPC productServiceRPC) {
        this.productServiceRPC = productServiceRPC;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        return ResponseEntity.ok().body(productServiceRPC.getProductById(Integer.parseInt(id)));
    }


    @GetMapping("/product/category/{id}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable String id) {
        return ResponseEntity.ok().body(productServiceRPC.getProductByCategoryId(Integer.parseInt(id)));
    }

}
