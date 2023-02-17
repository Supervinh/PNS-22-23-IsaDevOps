package cli.model;

public class CliPayment {

    private String amount;

    public CliPayment(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    //    public CliPayment(double amount) {
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

    @Override
    public String toString() {
        return "CliPayment{" +
                "amount=" + amount +
                '}';
    }
}
