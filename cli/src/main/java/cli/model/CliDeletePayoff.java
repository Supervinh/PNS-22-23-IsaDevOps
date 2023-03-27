package cli.model;

public class CliDeletePayoff {
    private String storeName;
    private String payoffName;

    public CliDeletePayoff(String storeName, String payoffName) {
        this.storeName = storeName;
        this.payoffName = payoffName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPayoffName() {
        return payoffName;
    }

    public void setPayoffName(String payoffName) {
        this.payoffName = payoffName;
    }
}
