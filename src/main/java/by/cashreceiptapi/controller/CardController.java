package by.cashreceiptapi.controller;

import by.cashreceiptapi.dao.CardRepository;
import by.cashreceiptapi.exception.ResourceNotFoundException;
import by.cashreceiptapi.model.Card;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardRepository CardRepository;

    @GetMapping("/cards")
    public Iterable<Card> getAllCards() {
        return CardRepository.findAll();
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable(value = "id") Long CardId)
            throws ResourceNotFoundException {
        Card Card = CardRepository.findById(CardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card " + CardId + " not found"));
        return ResponseEntity.ok().body(Card);
    }

    @PostMapping("/cards")
    public Card createCard(@Valid @RequestBody Card Card) {
        return CardRepository.save(Card);
    }

    @PutMapping("/cards/{id}")
    public ResponseEntity<Card> updateCard(@PathVariable(value="id") Long CardId, @Valid @RequestBody Card CardDetails) throws ResourceNotFoundException {
        Card Card = CardRepository.findById(CardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card " + CardId + " not found"));

        Card.setDiscount(CardDetails.getDiscount());

        Card updatedCard = CardRepository.save(Card);
        return ResponseEntity.ok(updatedCard);
    }

    @DeleteMapping("/cards/{id}")
    public Map<String, Boolean> deleteCard(@PathVariable(value="id") Long CardId) throws ResourceNotFoundException {
        Card Card = CardRepository.findById(CardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card " + CardId + " not found"));

        CardRepository.delete(Card);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
