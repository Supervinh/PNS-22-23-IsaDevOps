package cli.model;

import java.util.UUID;

public class CliPayoff {
    private UUID id;
    private String name;
    private double cost;
    private int pointCost;
    private CliStore store;


    public CliPayoff() {
    }

    public CliPayoff(String name, double cost, int pointCost, CliStore store, UUID id) {
        this.name = name;
        this.cost = cost;
        this.pointCost = pointCost;
        this.store = store;
        this.id = id;
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

    public CliStore getStore() {
        return store;
    }

    public void setStore(CliStore store) {
        this.store = store;
    }


    @Override
    public String toString() {
        return "CliPayoff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", pointCost=" + pointCost +
                '}';
    }
}
