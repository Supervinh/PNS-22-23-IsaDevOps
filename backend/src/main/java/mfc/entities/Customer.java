package mfc.entities;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Customer extends Account {
    private String matriculation;
    private int fidelityPoints;
    private double balance;
    private LocalDate vfp;
    private String creditCard;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private List<Store> favoriteStores;
    private LocalDateTime lastConnexion;

    public Customer() {
    }

    public Customer(String name, String mail, String password, String creditCard) {
        super(name, mail, password);
        this.creditCard = creditCard;
        //Initiate vfp to yesterday, to avoid any advantage before the acquisition of the status
        vfp = LocalDate.now().minus(Period.ofDays(1));
        fidelityPoints = 0;
        balance = 0;
        matriculation = "";
        favoriteStores = new ArrayList<>();
    }

    public String getMatriculation() {
        return matriculation;
    }

    public void setMatriculation(String matriculation) {
        this.matriculation = matriculation;
    }

    public int getFidelityPoints() {
        return fidelityPoints;
    }

    public void setFidelityPoints(int fidelityPoints) {
        this.fidelityPoints = fidelityPoints;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Store> getFavoriteStores() {
        return favoriteStores;
    }

    public void setFavoriteStores(List<Store> favoriteStores) {
        this.favoriteStores = favoriteStores;
    }

    public LocalDate getVfp() {
        return vfp;
    }

    public void setVfp(LocalDate vfp) {
        this.vfp = vfp;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(@Pattern(regexp = "\\d{10}+", message = "credit card should be exactly 10 digits") String creditCard) {
        this.creditCard = creditCard;
    }

    public LocalDateTime getLastConnexion() {
        return lastConnexion;
    }

    public void setLastConnexion(LocalDateTime lastConnexion) {
        this.lastConnexion = lastConnexion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return fidelityPoints == customer.fidelityPoints && Double.compare(customer.balance, balance) == 0 && Objects.equals(matriculation, customer.matriculation) && favoriteStores.equals(customer.favoriteStores) && Objects.equals(vfp, customer.vfp) && Objects.equals(creditCard, customer.creditCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), matriculation, fidelityPoints, balance, favoriteStores, vfp, creditCard);
    }

    @Override
    public String toString() {
        return "Customer{" + getName() + '\'' +
                "matriculation='" + matriculation + '\'' +
                ", fidelityPoints=" + fidelityPoints +
                ", balance=" + balance +
                ", favoriteStores=" + favoriteStores +
                ", vfp=" + vfp +
                ", creditCard='" + creditCard + '\'' +
                '}';
    }
}
