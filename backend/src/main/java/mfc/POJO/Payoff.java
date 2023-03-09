package mfc.POJO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Payoff {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double cost;
    private int pointCost;
    @ManyToOne
    private Store store;

    public Payoff(String name, double cost, int pointCost, Store store) {
        this.name = name;
        this.cost = cost;
        this.pointCost = pointCost;
        this.store = store;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payoff payoff = (Payoff) o;
        return Double.compare(payoff.cost, cost) == 0 && pointCost == payoff.pointCost && Objects.equals(name, payoff.name) && Objects.equals(store, payoff.store);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost, pointCost, store);
    }

    public Payoff() {

    }

    public Long getId() {
        return id;
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

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
