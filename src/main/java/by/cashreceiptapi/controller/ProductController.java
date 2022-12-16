package by.cashreceiptapi.controller;

import by.cashreceiptapi.dao.ProductRepository;
import by.cashreceiptapi.exception.ResourceNotFoundException;
import by.cashreceiptapi.model.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductRepository ProductRepository;
    
    @GetMapping("/products")
    public Iterable<Product> getAllProducts() {
        return ProductRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long ProductId)
            throws ResourceNotFoundException {
        Product Product = ProductRepository.findById(ProductId)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + ProductId + " not found"));
        return ResponseEntity.ok().body(Product);
    }

    @PostMapping("/products")
    public Product createProduct(@Valid @RequestBody Product Product) {
        return ProductRepository.save(Product);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value="id") Long ProductId, @Valid @RequestBody Product ProductDetails) throws ResourceNotFoundException {
        Product Product = ProductRepository.findById(ProductId)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + ProductId + " not found"));

        Product.setName(ProductDetails.getName());
        Product.setPrice(ProductDetails.getPrice());
        Product.setPromo(ProductDetails.getPromo());

        Product updatedProduct = ProductRepository.save(Product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/products/{id}")
    public Map<String, Boolean> deleteProduct(@PathVariable(value="id") Long ProductId) throws ResourceNotFoundException {
        Product Product = ProductRepository.findById(ProductId)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + ProductId + " not found"));

        ProductRepository.delete(Product);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}