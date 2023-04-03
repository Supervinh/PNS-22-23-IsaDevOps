package mfc.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class PayoffPurchase {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double cost;
    private int pointCost;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Store store;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;

    public PayoffPurchase(String name, double cost, int pointCost, Store store, Customer customer) {
        this.name = name;
        this.cost = cost;
        this.pointCost = pointCost;
        this.store = store;
        this.customer = customer;
    }

    public PayoffPurchase(Payoff payoff, Customer customer) {
        this.name = payoff.getName();
        this.cost = payoff.getCost();
        this.pointCost = payoff.getPointCost();
        this.store = payoff.getStore();
        this.customer = customer;
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

    public Store getStore() {
        return store;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayoffPurchase that)) return false;
        return Double.compare(that.cost, cost) == 0 && pointCost == that.pointCost /*&& Objects.equals(id, that.id)*/ && Objects.equals(name, that.name) && Objects.equals(store, that.store) && Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cost, pointCost, store, customer);
    }

    @Override
    public String toString() {
        return "PayoffPurchase{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", pointCost=" + pointCost +
                ", storeName='" + store.getName() + '\'' +
                ", customerEmail='" + customer.getMail() + '\'' +
                '}';
    }
}