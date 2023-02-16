package cli.model;

import java.time.LocalDate;
import java.util.UUID;

public class CliPurchase {
    private double cost;
    private LocalDate date;
    private CliCustomer user;
    private UUID id;

    public CliPurchase (double c, CliCustomer u){
        cost = c;
        user = u;
    }

    public void setId(UUID id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "CliPurchase{" +
                "cost=" + cost +
                ", date=" + date +
                ", user=" + user +
                ", id=" + id +
                '}';
    }
}

