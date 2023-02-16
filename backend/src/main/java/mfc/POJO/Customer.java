package mfc.POJO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer extends Account {
    private String matriculation;
    private int fidelityPoints;
    private double balance;

    private String creditCard;
    private List<Store> favoriteStores;
    private LocalDate vfp;

    public Customer(String name, String mail, String password, String matriculation, String creditCard) {
        super(name, mail, password);
        this.matriculation = matriculation;
        this.fidelityPoints = 0;
        this.balance = 0;
        this.creditCard = creditCard;
        this.favoriteStores = new ArrayList<>();
        this.vfp = null;
    }

    public Customer(String name, String mail, String password) {
        super(name, mail, password);
        this.matriculation = null;
        this.fidelityPoints = 0;
        this.balance = 0;
        this.creditCard = null;
        this.favoriteStores = new ArrayList<>();
        this.vfp = null;
    }

    public String getMatriculation() {
        return matriculation;
    }

    public int getFidelityPoints() {
        return fidelityPoints;
    }

    public double getBalance() {
        return balance;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public List<Store> getFavoriteStores() {
        return favoriteStores;
    }

    public LocalDate getVfp() {
        return vfp;
    }

    public void setMatriculation(String matriculation) {
        this.matriculation = matriculation;
    }

    public void setFidelityPoints(int fidelityPoints) {
        this.fidelityPoints = fidelityPoints;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public void setFavoriteStores(List<Store> favoriteStores) {
        this.favoriteStores = favoriteStores;
    }

    public void setVfp(LocalDate vfp) {
        this.vfp = vfp;
    }
}
