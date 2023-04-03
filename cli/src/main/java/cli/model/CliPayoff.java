package cli.model;


public class CliPayoff {
    private Long id;
    private String name;
    private double cost;
    private int pointCost;
    private String storeName;
    private boolean isVfp;

    public CliPayoff(String name, double cost, int pointCost, String storeName, boolean isVfp) {
        this.name = name;
        this.cost = cost;
        this.pointCost = pointCost;
        this.storeName = storeName;
        this.isVfp = isVfp;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public boolean isVfp() {
        return isVfp;
    }

    public void setVfp(boolean vfp) {
        isVfp = vfp;
    }

    @Override
    public String toString() {
        return "CliPayoff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", pointCost=" + pointCost +
                ", storeName='" + storeName + '\'' +
                ", isVfp=" + isVfp +
                '}';
    }
}
