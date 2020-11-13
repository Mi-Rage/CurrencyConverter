package geekbrains.currencyconverter.model;

import java.util.List;

public class Pairs {

    private String pairName;
    private String pairPrice;
    private int quantity;
    private String amount = "0";
    private String sourceCurrency;
    private String requiredCurrency;
    private String date;
    private List<Rate> rateList;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Rate> getRateList() {
        return rateList;
    }

    public void setRateList(List<Rate> rateList) {
        this.rateList = rateList;
    }
}

