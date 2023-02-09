package cli.model;

import java.time.LocalDate;
import java.util.List;

public class CliCustomer {
    private String matriculation;
    private int points;
    private double balance;

    private String creditCard;
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
