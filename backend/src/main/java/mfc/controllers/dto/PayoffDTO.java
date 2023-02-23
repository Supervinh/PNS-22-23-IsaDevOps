package mfc.controllers.dto;

import java.util.UUID;

public class PayoffDTO {
    private UUID id;
    private String name;
    private double cost;
    private int pointCost;
    private String storeName;


    public PayoffDTO() {
    }

    public PayoffDTO(UUID id, String name, double cost, int pointCost, String storeName) {
        this.name = name;
        this.cost = cost;
        this.pointCost = pointCost;
        this.storeName = storeName;
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

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public String toString() {
        return "PayoffDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", pointCost=" + pointCost +
                ", storeName='" + storeName + '\'' +
                '}';
    }
}
