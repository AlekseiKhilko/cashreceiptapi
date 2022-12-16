package by.cashreceiptapi.cashreceipt;

import by.cashreceiptapi.model.*;
import by.cashreceiptapi.dao.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items = new ArrayList<CartItem>();
    private ProductRepository productRepository;
    private Integer vatRate;
    private Integer promoDiscount = 10;
    private Integer promoProductCount = 5;
    private Double vatValue = 0d;
    private Double taxableTotalValue = 0d;
    private Double discountValue = 0d;
    private Double totalValue = 0d;
    private Card card = null;
    private ProductPromo productPromo;

    public Cart(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Cart(ProductRepository productRepository, Card card) {
        this.productRepository = productRepository;
        this.card = card;
    }

    public void addItem(Long id, Integer quantity)  {
        if(id < 1 || quantity < 1){
            throw new IllegalArgumentException("The product id or quantity is less than 1");
        }
        Product product = productRepository.findById(id).get();
        items.add(new CartItem(product, quantity));
    }
    public void calculate()  {
        Double taxableTotal = 0d;

        for(CartItem cartItem: items){
            if(cartItem.getProduct().getPromo()){
                cartItem = this.getProductPromo().calculate(cartItem);
            }

            Double price = cartItem.getProduct().getPrice();
            Integer quantity = cartItem.getQuantity();
            Double totalItem = price * quantity;
            cartItem.setTotal(totalItem);
            taxableTotal += totalItem;
        }

        if(this.cardExists()){
            Double discountPercent = this.getCard().getDiscount().doubleValue();
            Double discount = taxableTotal * (discountPercent / 100d);
            this.setDiscountValue(discount);
        }

        this.setTaxableTotalValue(taxableTotal);
        taxableTotal -= this.getDiscountValue();
        Double vatValue = taxableTotal * (this.getVatRate()/100d);
        this.setVatValue(vatValue);
        taxableTotal += vatValue;
        this.setTotalValue(taxableTotal);
    }
    public List<CartItem> getItems() {
        return items;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Boolean cardExists() {
        return this.card == null ? false: true;
    }
    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Integer getVatRate() {
        return vatRate;
    }

    public void setVatRate(Integer vatRate) {
        this.vatRate = vatRate;
    }

    public Double getVatValue() {
        return vatValue;
    }

    public void setVatValue(Double vatValue) {
        this.vatValue = vatValue;
    }

    public Double getTaxableTotalValue() {
        return taxableTotalValue;
    }

    public void setTaxableTotalValue(Double taxableTotalValue) {
        this.taxableTotalValue = taxableTotalValue;
    }

    public ProductPromo getProductPromo() {
        return productPromo;
    }

    public void setProductPromo(ProductPromo productPromo) {
        this.productPromo = productPromo;
    }
}
