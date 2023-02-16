package mfc.POJO;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Customer extends Account {
    private String matriculation;
    private int fidelityPoints;
    private double balance;
    private List<Store> favoriteStores;
    private LocalDate vfp;
    private String creditCard;

    public Customer(String name, String creditCard) {
        this(name, "", "", creditCard);
    }

    public Customer(String name, String mail, String password, String creditCard) {
        super(name, mail, password);
        this.creditCard = creditCard;
        this.setId(UUID.randomUUID());
        //Initiate vfp to yesterday, to avoid any advantage before the acquisition of the status
        vfp = LocalDate.now().minus(Period.ofDays(1));
        fidelityPoints = 0;
        balance = 0;
        matriculation = "";
        favoriteStores = new ArrayList<>();
    }

//    private void init(String creditCard) {
//        this.creditCard = creditCard;
//        this.setId(UUID.randomUUID());
//        //Initiate vfp to yesterday, to avoid any advantage before the acquisition of the status
//        vfp =  LocalDate.now().minus(Period.ofDays(1));
//        fidelityPoints= 0;
//        balance = 0;
//        matriculation ="";
//        favoriteStores = new ArrayList<>();
//    }

    //
//    public Customer(String name, String mail, String password, String matriculation, String creditCard) {
//        super(name, mail, password);
//        this.matriculation = matriculation;
//        this.fidelityPoints = 0;
//        this.balance = 0;
//        this.creditCard = creditCard;
//        this.favoriteStores = new ArrayList<>();
//        this.vfp = null;
//    }
//
//    public Customer(String name, String mail, String password) {
//        super(name, mail, password);
//        this.matriculation = null;
//        this.fidelityPoints = 0;
//        this.balance = 0;
//        this.creditCard = null;
//        this.favoriteStores = new ArrayList<>();
//        this.vfp = null;
//    }

    public String getMatriculation() {
        return matriculation;
    }

    public int getFidelityPoints() {
        return fidelityPoints;
    }

    public double getBalance() {
        return balance;
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

    public void setFavoriteStores(List<Store> favoriteStores) {
        this.favoriteStores = favoriteStores;
    }

    public void setVfp(LocalDate vfp) {
        this.vfp = vfp;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        if (!getId().equals(customer.getId())) return false;
        if (!getName().equals(customer.getName())) return false;
        return getCreditCard().equals(customer.getCreditCard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), creditCard);
    }

}
