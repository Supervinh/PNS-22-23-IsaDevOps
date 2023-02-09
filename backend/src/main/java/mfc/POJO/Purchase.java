package mfc.POJO;

import java.time.LocalDate;

public class Purchase {
    private double cost;
    private LocalDate date;
    private Customer user;

    public Purchase (double c, Customer u){
        cost = c;
        date = LocalDate.now();
        user = u;
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
}
