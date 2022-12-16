package by.cashreceiptapi.cashreceipt;

import by.cashreceiptapi.cashreceipt.CartItem;

public class ProductPromo {
    private Integer discount;
    private Integer nubmerOfProducts;

    public ProductPromo(Integer discount, Integer nubmerOfProducts) {
        this.discount = discount;
        this.nubmerOfProducts = nubmerOfProducts;
    }

    public CartItem calculate(CartItem cartItem) {
        if(cartItem.getQuantity() > this.getNubmerOfProducts()) {
            Double discount = this.getDiscount().doubleValue();
            Double price = cartItem.getProduct().getPrice();
            Double newPrice = price - price * (discount / 100d);
            cartItem.getProduct().setOldPrice(price);
            cartItem.getProduct().setPrice(newPrice);

        }
        return cartItem;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getNubmerOfProducts() {
        return nubmerOfProducts;
    }

    public void setNubmerOfProducts(Integer nubmerOfProducts) {
        this.nubmerOfProducts = nubmerOfProducts;
    }
}
