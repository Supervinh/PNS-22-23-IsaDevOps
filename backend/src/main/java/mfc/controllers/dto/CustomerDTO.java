package mfc.controllers.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.UUID;

public class CustomerDTO {

    private UUID id; // expected to be empty when POSTing the creation of Customer, and containing the UUID when returned

    @NotBlank(message = "name should not be blank")
    private String name;
    private String mail;
    private String password;
//    private double balance;

    @Pattern(regexp = "\\d{10}+", message = "credit card should be exactly 10 digits")
    private String creditCard;

    public CustomerDTO(UUID id, String name, String mail, String password, String creditCard) {
        this.id = id;
        this.name = name;
        this.creditCard = creditCard;
        this.mail = mail;
        this.password = password;
//        balance=0;
    }

//    public CustomerDTO(UUID id, String name, String mail, String password, double balance, String creditCard) {
//        this.id = id;
//        this.name = name;
//        this.mail = mail;
//        this.password = password;
//        this.balance = balance;
//        this.creditCard = creditCard;
//    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
