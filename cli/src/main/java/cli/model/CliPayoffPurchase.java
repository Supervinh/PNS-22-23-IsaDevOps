package cli.model;

public class CliPayoffPurchase {
    private Long id;
    private String name;
    private double cost;
    private int pointCost;
    private String storeName;
    private String customerEmail;

    public CliPayoffPurchase(String name, double cost, int pointCost, String storeName, String customerEmail) {
        this.name = name;
        this.cost = cost;
        this.pointCost = pointCost;
        this.storeName = storeName;
        this.customerEmail = customerEmail;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getPointCost() {
        return pointCost;
    }

    public void setPointCost(int pointCost) {
        this.pointCost = pointCost;
    }

    @Override
    public String toString() {
        return "CliPayoffPurchase{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", pointCost=" + pointCost +
                ", storeName='" + storeName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                '}';
    }
}

