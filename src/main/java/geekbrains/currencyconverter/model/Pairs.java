package geekbrains.currencyconverter.model;

public class Pairs {

    private String pairName;
    private String pairPrice;
    private int quantity;
    private String amount = "0";
    private String sourceCurrency;
    private String requiredCurrency;

    public Pairs() {
    }

    public String getPairName() {
        return pairName;
    }

    public void setPairName(String pairName) {
        this.pairName = pairName;
    }

    public String getPairPrice() {
        return pairPrice;
    }

    public void setPairPrice(String pairPrice) {
        this.pairPrice = pairPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getRequiredCurrency() {
        return requiredCurrency;
    }

    public void setRequiredCurrency(String requiredCurrency) {
        this.requiredCurrency = requiredCurrency;
    }
}

