package mfc.controllers.dto;

public class PaymentDTO {

    //    @Positive(message = "amount should be positive")
    private String amount;

    public PaymentDTO(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    //    public PaymentDTO(double amount) {
//        this.amount = amount;
//    }
//
//    public double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(double amount) {
//        this.amount = amount;
//    }
}
