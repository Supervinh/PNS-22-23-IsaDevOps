package mfc.POJO;

import java.time.LocalDate;
import java.util.UUID;

public class Purchase {
    private double cost;
    private LocalDate date;
    private Customer user;
    private UUID id;

    public Purchase (double c, Customer u){
        cost = c;
        date = LocalDate.now();
        user = u;
        id = UUID.randomUUID();
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

    public UUID getId() {
        return id;
    }
}
