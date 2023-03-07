package mfc.POJO;

import java.util.UUID;

public class Payoff {

    private UUID id;
    private String name;
    private double cost;
    private int pointCost;
    private Store store;

    public Payoff(String name, double cost, int pointCost, Store store) {
        this.name = name;
        this.cost = cost;
        this.pointCost = pointCost;
        this.store = store;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
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
