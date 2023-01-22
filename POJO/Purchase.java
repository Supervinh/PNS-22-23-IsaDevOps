package POJO;

import java.time.LocalDate;

public class Purchase {
    private double cost;
    private LocalDate date;
    private Customer user;
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
}
