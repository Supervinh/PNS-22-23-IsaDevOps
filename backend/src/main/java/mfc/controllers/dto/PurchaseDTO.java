package mfc.controllers.dto;

import java.util.UUID;

public class PurchaseDTO {

    private UUID id;
    private String customerEmail;
    private double cost;
    private String storeName;
    private boolean internalAccount; // Is the purchase using the internal account or already paid?

    public PurchaseDTO() {
    }

    public PurchaseDTO(UUID id, String customerEmail, double cost, String storeName, boolean internalAccount) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.cost = cost;
        this.storeName = storeName;
        this.internalAccount = internalAccount;
    }

    public boolean isInternalAccount() {
        return internalAccount;
    }

    public void setInternalAccount(boolean internalAccount) {
        this.internalAccount = internalAccount;
    }

    public UUID getId() {
        return id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}