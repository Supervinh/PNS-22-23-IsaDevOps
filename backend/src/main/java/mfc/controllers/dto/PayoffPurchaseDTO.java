package mfc.controllers.dto;

import java.util.UUID;

public class PayoffPurchaseDTO {
    private UUID id;
    private String name;
    private double cost;
    private int pointCost;
    private String storeName;
    private String customerEmail;

    public PayoffPurchaseDTO(String name, double cost, int pointCost, String storeName, String customerEmail) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.cost = cost;
        this.pointCost = pointCost;
        this.storeName = storeName;
        this.customerEmail = customerEmail;
    }

    public PayoffPurchaseDTO(UUID id, String name, double cost, int pointCost, String storeName, String customerEmail) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.pointCost = pointCost;
        this.storeName = storeName;
        this.customerEmail = customerEmail;
    }

    public UUID getId() {
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
}
