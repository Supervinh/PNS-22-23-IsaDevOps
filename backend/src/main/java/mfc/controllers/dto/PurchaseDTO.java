package mfc.controllers.dto;

import java.util.UUID;

public class PurchaseDTO {

    private UUID id;
    private CustomerDTO customerDTO;
    private double cost;
    private StoreDTO storeDTO;

    public PurchaseDTO(UUID id, CustomerDTO customerDTO, double cost, StoreDTO storeDTO) {
        this.id = id;
        this.customerDTO = customerDTO;
        this.cost = cost;
        this.storeDTO = storeDTO;
    }

    public UUID getId() {
        return id;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public StoreDTO getStoreDTO() {
        return storeDTO;
    }

    public void setStoreName(StoreDTO storeDTO) {
        this.storeDTO = storeDTO;
    }

}