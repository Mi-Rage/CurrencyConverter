package geekbrains.currencyconverter.model;

public class PairsDTO {
    private String pairNameDTO;
    private String quantityDTO = "0";

    public String getPairNameDTO() {
        return pairNameDTO;
    }

    public void setPairNameDTO(String pairNameDTO) {
        this.pairNameDTO = pairNameDTO;
    }

    public String getQuantityDTO() {
        return quantityDTO;
    }

    public void setQuantityDTO(String quantityDTO) {
        this.quantityDTO = quantityDTO;
    }
}
