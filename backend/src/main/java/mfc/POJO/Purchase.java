package mfc.POJO;

import java.time.LocalDate;
import java.util.UUID;

public class Purchase {
    private double cost;
    private LocalDate date;
    private Customer customer;
    private UUID id;

    public Store store;

    public Purchase(double c, Customer u, Store s) {
        cost = c;
        date = LocalDate.now();
        customer = u;
        id = UUID.randomUUID();
        store = s;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public UUID getId() {
        return id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
