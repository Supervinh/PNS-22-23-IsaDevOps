package cli.model;

import java.util.UUID;

public class CliCustomer {
    private UUID id;

    private String name;

    private int fidelityPoints;

    private String creditCard;

    public CliCustomer (String n, int fp, String cc){
        name = n;
        fidelityPoints = fp;
        creditCard = cc;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", fidelityPoints='" + fidelityPoints + '\'' +
                ", creditCard='" + creditCard + '\'' +
                '}';
    }
}
