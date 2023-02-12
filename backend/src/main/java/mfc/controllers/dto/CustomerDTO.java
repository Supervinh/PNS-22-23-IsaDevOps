package mfc.controllers.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.UUID;

public class CustomerDTO {
    private UUID id;

    @NotBlank(message = "name should not be blank")
    private String name;

    @NotBlank(message = "mail should not be blank")
    private String mail;

    @NotBlank(message = "password should not be blank")
    private String password;

    private int fidelityPoints;

    @Pattern(regexp = "\\d{10}+", message = "credit card should be exactly 10 digits")
    private String creditCard;

    public CustomerDTO(UUID id, String name, int fidelityPoints, String creditCard) {
        this.id = id;
        this.name = name;
        this.fidelityPoints = fidelityPoints;
        this.creditCard = creditCard;
    }

    public CustomerDTO(UUID id, String name, String mail, String password) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
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

    public int getFidelityPoints() {
        return fidelityPoints;
    }

    public void setFidelityPoints(int fidelityPoints) {
        this.fidelityPoints = fidelityPoints;
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

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }
}
