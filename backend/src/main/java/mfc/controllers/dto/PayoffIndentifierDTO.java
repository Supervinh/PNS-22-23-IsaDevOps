package mfc.controllers.dto;

public class PayoffIndentifierDTO {

    private String storeName;
    private String payOffName;

    public PayoffIndentifierDTO(String storeName, String payOffName) {
        this.storeName = storeName;
        this.payOffName = payOffName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPayOffName() {
        return payOffName;
    }

    public void setPayOffName(String payOffName) {
        this.payOffName = payOffName;
    }
}
