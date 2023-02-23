package mfc.controllers.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.UUID;

public class CustomerDTO {

    private UUID id; // expected to be empty when POSTing the creation of Customer, and containing the UUID when returned

    @NotBlank(message = "name should not be blank")
    private String name;
    @Email(message = "mail should be a valid email")
    @NotBlank(message = "mail should not be blank")
    private String mail;
    @NotBlank(message = "password should not be blank")
    private String password;
    //    @Positive
    private double balance;

    private String creditCard;


    public CustomerDTO(UUID id, String name, String mail, String password, String creditCard) {
        this.id = id;
        this.name = name;
        this.creditCard = creditCard;
        this.mail = mail;
        this.password = password;
        balance = 0;
    }


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


    public void setCreditCard( @Pattern(regexp = "\\d{10}+", message = "credit card should be exactly 10 digits") String creditCard) {
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
