package cli.model;

public class CliPayoffIdentifier {

    private String storeName;
    private String payOffName;

    public CliPayoffIdentifier(String storeName, String payOffName) {
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
