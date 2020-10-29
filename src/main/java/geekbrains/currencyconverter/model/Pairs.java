package geekbrains.currencyconverter.model;

public class Pairs {

    private String pairName;
    private String pairPrice;
    private String quantity = "0";
    private String amount = "0";

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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}

