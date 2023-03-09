package mfc.POJO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Purchase {
    @ManyToOne
    public Store store;
    private double cost;
    private LocalDate date;
    @ManyToOne
    private Customer customer;
    @Id
    @GeneratedValue
    private Long id;

    public Purchase(double c, Customer u, Store s) {
        cost = c;
        date = LocalDate.now();
        customer = u;
        store = s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Double.compare(purchase.cost, cost) == 0 && Objects.equals(store, purchase.store) && Objects.equals(date, purchase.date) && Objects.equals(customer, purchase.customer) && Objects.equals(id, purchase.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(store, cost, date, customer);
    }

    public Purchase() {

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

    public Long getId() {
        return id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
