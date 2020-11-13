package geekbrains.currencyconverter.model;

/**
 * Класс значений курса валюты относительно даты
 */
public class Rate {
    private float rateValue;
    private Long timeStamp;

    public Rate() {
    }

    public float getRateValue() {
        return rateValue;
    }

    public void setRateValue(float rateValue) {
        this.rateValue = rateValue;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

}
