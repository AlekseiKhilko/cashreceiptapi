package by.cashreceiptapi.cashreceipt;

import by.cashreceiptapi.cashreceipt.Cart;
import by.cashreceiptapi.cashreceipt.CartItem;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CashReceiptPrint {
    Locale locale = new Locale("en", "US");
    private String textShop = "SUPERMARKT 123";
    private String textAddress = "123, MILKYWAY Galaxy/ Earth";
    private String textPhone = "Tel: 123-456-789";
    private String textDate;
    private String textTime;
    private Integer cashirNomber = 4435435;
    private NumberFormat currencyFormatter;
    private Integer cashReceiptWidthChar = 60;
    private Cart cart;

    public String getTextShop() {
        return textShop;
    }

    public String getTextAddress() {
        return textAddress;
    }

    public String getTextPhone() {
        return textPhone;
    }

    public Integer getCashirNomber() {
        return cashirNomber;
    }

    public String getTextDate() {
        return textDate;
    }

    public String getTextTime() {
        return textTime;
    }

    private List<String> lines = new ArrayList<>();

    private Map<String, Integer> columns = new HashMap<String, Integer>() {{
        put("Qty", 5);
        put("Description", 30);
        put("Price", 15);
        put("Total", 10);
    }};


    public CashReceiptPrint(Cart cart) {
        this.cart = cart;
        this.setCurrencyFormatter(NumberFormat.getCurrencyInstance(this.locale));
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        this.textDate = dateFormatter.format(calendar.getTime());

        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        this.textTime = timeFormatter.format(calendar.getTime());
    }

    public void printf(String format, Object ... args){
        String text = String.format(format, args);
        this.addLine(text);
        //System.out.print(text);
    }

    public void println(String text){
        this.addLine(text);
        //System.out.println(text);
    }

    public void addLine(String string){
        lines.add(string);
    }

    public String getPrintAsText(){
        return Arrays.toString(this.lines.toArray());
    }

    public void saveToFile(String filename){
        try {
            Path file = Paths.get(filename);
            Files.write(file, this.lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            // Report
        } finally {
            //try {writer.close();} catch (Exception ex) {}
        }
    }

    public CashReceiptPrint header(){
        this.printf("\n%s\n\n", this.padCenter("CASH RECEIPT", cashReceiptWidthChar));
        this.printf("%s\n%s\n%s\n\n",
                this.padCenter(textShop, cashReceiptWidthChar),
                this.padCenter(textAddress, cashReceiptWidthChar),
                this.padCenter(textPhone, cashReceiptWidthChar));


        this.printf("%s%s\n",
                this.padRight("CASHIER: №" + cashirNomber, cashReceiptWidthChar/2),
                this.padLeft("DATE: " + this.getTextDate(), cashReceiptWidthChar/2));


        this.println(padLeft("TIME: " + this.getTextTime(), cashReceiptWidthChar));

        return this;
    }

    public Map<String, Integer> getColumns() {
        return columns;
    }
    public Integer getColumnWidth(String column) {
        return columns.get(column);
    }
    public void setColumns(Map<String, Integer> columns) {
        this.columns = columns;
    }

    public CashReceiptPrint body(){

        this.printf("%s%s%s%s\n",
                this.padRight("QTY", this.getColumnWidth("Qty")),
                this.padRight("DESCRIPTION", this.getColumnWidth("Description")),
                this.padLeft("PRICE", this.getColumnWidth("Price")),
                this.padLeft("TOTAL", this.getColumnWidth("Total")));

        for(CartItem item: cart.getItems()){
            Integer quantity = item.getQuantity();
            String name = item.getProduct().getName();
            Double price = item.getProduct().getPrice();
            Double total = item.getTotal();

            if(name.length() > this.getColumnWidth("Description")){
                name = name.substring(0, this.getColumnWidth("Description") -4) + "...";
            }

            String oldPriceString = "";
            if(item.getProduct().getOldPrice() != null) {
                Double oldPrice = item.getProduct().getOldPrice();
                oldPriceString = " (" + getCurrencyFormatter().format(oldPrice) + ")";
            }

            this.printf("%s%s%s%s\n",
                    this.padRight(quantity.toString(), this.getColumnWidth("Qty")),
                    this.padRight(name, this.getColumnWidth("Description")),
                    this.padLeft(getCurrencyFormatter().format(price) + oldPriceString, this.getColumnWidth("Price")),
                    this.padLeft(getCurrencyFormatter().format(total), this.getColumnWidth("Total")));

        }

        return this;
    }
    public CashReceiptPrint footer(){
        String discountValue = "";
        if(cart.getDiscountValue() > 0d){
            discountValue = getCurrencyFormatter().format(cart.getDiscountValue());
        }
        this.printf("%s%s\n",
                padRight("TAXABLE TOT.", cashReceiptWidthChar/2),
                padLeft(getCurrencyFormatter().format(cart.getTaxableTotalValue()), cashReceiptWidthChar/2));

        this.printf("%s%s\n",
                padRight("DISCOUNT CARD", cashReceiptWidthChar/2),
                padLeft("-" + discountValue, cashReceiptWidthChar/2));

        this.printf("%s%s\n",
                padRight("VAT" + cart.getVatRate() + "%", cashReceiptWidthChar/2),
                padLeft(getCurrencyFormatter().format(cart.getVatValue()), cashReceiptWidthChar/2));

        this.printf("%s%s\n",
                padRight("TOTAL", cashReceiptWidthChar/2),
                padLeft(getCurrencyFormatter().format(cart.getTotalValue()), cashReceiptWidthChar/2));

        return this;
    }

    public CashReceiptPrint line(char padChar){
        String line = new String(new char[cashReceiptWidthChar]).replace('\0', padChar);
        this.println(line);

        return this;
    }

    public NumberFormat getCurrencyFormatter() {
        return currencyFormatter;
    }

    public void setCurrencyFormatter(NumberFormat currencyFormatter) {
        this.currencyFormatter = currencyFormatter;
    }

    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    public static String padCenter (String s, int width) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }
}
