package by.cashreceiptapi.cashreceipt;

import by.cashreceiptapi.cashreceipt.Cart;
import by.cashreceiptapi.cashreceipt.CashReceiptPrint;
import java.text.NumberFormat;
import java.util.Locale;

public class CashReceipt {
    Locale locale = new Locale("en", "US");
    NumberFormat currencyFormatter;
    private String textShop = "SUPERMARKT 123";
    private String textAddress = "123, MILKYWAY Galaxy/ Earth";
    private String textPhone = "Tel: 123-456-789";
    private Integer cashirNomber = 4435435;

    private Integer cashReceiptWidthChar = 44;
    private Cart cart;
    private CashReceiptPrint cashReceiptPrint;

    public CashReceipt(Cart cart) {
        this.cart = cart;
        this.setCurrencyFormatter(NumberFormat.getCurrencyInstance(this.locale));
        this.cashReceiptPrint = new CashReceiptPrint(cart);
    }

    public CashReceiptPrint getCashReceiptPrint() {
        return cashReceiptPrint;
    }

    public void setCashReceiptPrint(CashReceiptPrint cashReceiptPrint) {
        this.cashReceiptPrint = cashReceiptPrint;
    }

    public String print() {
         return this.getCashReceiptPrint()
                .header()
                .line('-')
                .body()
                .line('=')
                .footer()
                .getPrintAsText();
    }

    public void save(String filename) {
        this.getCashReceiptPrint().saveToFile(filename);
    }
    public NumberFormat getCurrencyFormatter() {
        return currencyFormatter;
    }

    public void setCurrencyFormatter(NumberFormat currencyFormatter) {
        this.currencyFormatter = currencyFormatter;
    }

}
