package mfc.POJO;

import java.time.LocalDate;
import java.util.List;

public class Customer extends Account {
    private String matriculation;
    private int points;
    private double balance;

    private String creditCard;
    private List<Store> preferredStores;
    private LocalDate vfp;

    public String getMatriculation(){
        return matriculation;
    }

    public int getPoints() {
        return points;
    }

    public double getBalance() {
        return balance;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public List<Store> getPreferredStores() {
        return preferredStores;
    }

    public LocalDate getVfp() {
        return vfp;
    }

}
