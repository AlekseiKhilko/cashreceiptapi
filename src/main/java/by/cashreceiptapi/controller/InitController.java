package by.cashreceiptapi.controller;

import by.cashreceiptapi.exception.ResourceNotFoundException;
import by.cashreceiptapi.model.Card;
import by.cashreceiptapi.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {
    @Autowired
    private by.cashreceiptapi.dao.ProductRepository ProductRepository;
    @Autowired
    private by.cashreceiptapi.dao.CardRepository CardRepository;

    @GetMapping("/init")
    public String init() {
        for (int p = 1; p < 9; p++) {
            Product product = new Product();
            product.setName("Test product " + p);
            product.setPrice((p < 4 ? 10.0d : 20.0d));
            product.setPromo((p < 4 ? true : false));
            ProductRepository.save(product);
        }
        for (int c = 1; c <= 2; c++) {
            Card card = new Card();
            card.setDiscount(10);
            CardRepository.save(card);
        }

        return "Initialized successfully";
    }
}