package mfc.controllers.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.UUID;

public class CustomerDTO {
    private UUID id;

    @NotBlank(message = "name should not be blank")
    private String name;

    private int fidelityPoints;

    @Pattern(regexp = "\\d{10}+", message = "credit card should be exactly 10 digits")
    private String creditCard;

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

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }
}
