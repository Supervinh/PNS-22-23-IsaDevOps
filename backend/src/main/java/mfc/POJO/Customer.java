package mfc.POJO;

import java.time.LocalDate;
import java.util.List;

public class Customer extends Account {
    private String matriculation;
    private int points;
    private double balance;

    private String creditCard;
    private List<Store> preferedStores;
    private LocalDate vfp;

    public int getPoints() {
        return points;
    }
    public String getMatriculation(){
        return matriculation;
    }
    public double getBalance() {
        return balance;
    }
}
