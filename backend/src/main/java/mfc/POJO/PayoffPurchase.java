package mfc.POJO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class PayoffPurchase {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double cost;
    private int pointCost;
    private String storeName;
    private String customerEmail;

    public PayoffPurchase(String name, double cost, int pointCost, String storeName, String customerEmail) {
        this.name = name;
        this.cost = cost;
        this.pointCost = pointCost;
        this.storeName = storeName;
        this.customerEmail = customerEmail;
    }

    public PayoffPurchase(Payoff payoff, Customer customer) {
        this.name = payoff.getName();
        this.cost = payoff.getCost();
        this.pointCost = payoff.getPointCost();
        this.storeName = payoff.getStore().getName();
        this.customerEmail = customer.getMail();
    }

    public PayoffPurchase() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public int getPointCost() {
        return pointCost;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayoffPurchase that)) return false;
        return Double.compare(that.cost, cost) == 0 && pointCost == that.pointCost /*&& Objects.equals(id, that.id)*/ && Objects.equals(name, that.name) && Objects.equals(storeName, that.storeName) && Objects.equals(customerEmail, that.customerEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cost, pointCost, storeName, customerEmail);
    }

    @Override
    public String toString() {
        return "PayoffPurchase{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", pointCost=" + pointCost +
                ", storeName='" + storeName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                '}';
    }
}