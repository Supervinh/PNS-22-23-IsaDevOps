package POJO;

import java.time.LocalDate;
import java.util.List;

public class Customer extends Account {
    private String matriculation;
    private int points;
    private double balance;

    private String creditCard;
    private List<Store> preferedStores;
    private LocalDate vfp;
}
