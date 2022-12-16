package by.cashreceiptapi.controller;

import by.cashreceiptapi.cashreceipt.CartItem;
import by.cashreceiptapi.cashreceipt.CashReceipt;
import by.cashreceiptapi.cashreceipt.Cart;
import by.cashreceiptapi.cashreceipt.ProductPromo;
import by.cashreceiptapi.dao.*;
import by.cashreceiptapi.exception.ResourceNotFoundException;
import by.cashreceiptapi.model.Card;
import by.cashreceiptapi.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.json.simple.JSONObject;

import java.util.*;

@RestController
@RequestMapping("/api")
public class CashreceiptController {
    @Autowired
    private ProductRepository ProductRepository;
    @Autowired
    private CardRepository CardRepository;

    @RequestMapping(value="/check", produces="application/json")
    public String  check(@RequestParam List<String> items, @RequestParam(required=false) Long card) throws ResourceNotFoundException {

        Cart cart = new Cart(ProductRepository);
        for(String item: items) {
            String[] parts = item.split("-");
            Long product = Long.parseLong(parts[0]);
            Integer quntity = Integer.parseInt(parts[1]);
            cart.addItem(product, quntity);
        }
        if(card != null) {
            Optional<Card> cardOptional = CardRepository.findById(card);
            if(cardOptional.isPresent()){
                cart.setCard(cardOptional.get());
            }else{
                new ResourceNotFoundException("Card " + card + " not found");
            }
        }
        ProductPromo productPromo = new ProductPromo(10, 5);
        cart.setProductPromo(productPromo);
        Integer vatRate = 17;
        cart.setVatRate(vatRate);
        cart.calculate();

        CashReceipt cashReceipt = new CashReceipt(cart);

        JSONObject jsonHeader = new JSONObject();
        jsonHeader.put("Date", cashReceipt.getCashReceiptPrint().getTextDate());
        jsonHeader.put("Time", cashReceipt.getCashReceiptPrint().getTextTime());
        jsonHeader.put("ShopName", cashReceipt.getCashReceiptPrint().getTextShop());
        jsonHeader.put("Address", cashReceipt.getCashReceiptPrint().getTextAddress());
        jsonHeader.put("Phone", cashReceipt.getCashReceiptPrint().getTextPhone());
        jsonHeader.put("CashirNomber", cashReceipt.getCashReceiptPrint().getCashirNomber().toString());

        JSONObject jsonBody = new JSONObject();
        for(CartItem catItem: cart.getItems()){
            JSONObject jsonItem = new JSONObject();
            jsonItem.put("id", catItem.getProduct().getId());
            jsonItem.put("name", catItem.getProduct().getName());
            jsonItem.put("price", catItem.getProduct().getPrice());
            if(catItem.getProduct().getOldPrice() !=null) {
                jsonItem.put("oldPrice", catItem.getProduct().getOldPrice());
            }
            jsonItem.put("quantity", catItem.getQuantity());
            jsonItem.put("total", catItem.getTotal());
            jsonBody.put(catItem.getProduct().getId(), jsonItem);
        }

        JSONObject jsonFooter= new JSONObject();
        jsonFooter.put("TaxableTotal", cart.getTaxableTotalValue().toString());
        if(cart.getDiscountValue() > 0d) {
            jsonFooter.put("Discount", cart.getDiscountValue().toString());
        }
        jsonFooter.put("Vat", cart.getVatValue().toString());
        jsonFooter.put("Total", cart.getTotalValue().toString());

        JSONObject jsonPrint= new JSONObject();
        jsonPrint.put("CashReceipt", cashReceipt.print());

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("Header", jsonHeader);
        jsonResponse.put("Body", jsonBody);
        jsonResponse.put("Footer", jsonFooter);
        jsonResponse.put("Print", jsonPrint);

        return jsonResponse.toJSONString();
    }
}
