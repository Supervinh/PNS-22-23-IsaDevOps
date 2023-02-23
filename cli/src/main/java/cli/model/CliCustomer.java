package cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

// A cli side class being equivalent to the backend CustomerDTO, in terms of attributes
// so that the automatic JSON (de-)/serialization will make the two compatible on each side
public class CliCustomer extends CliAccount {

    private UUID id; // expected to be empty when POSTing the creation of Customer, and containing the UUID when returned
    private double balance;
    private String creditCard;

    @JsonCreator
    public CliCustomer(@JsonProperty("name") String name, @JsonProperty("mail") String mail, @JsonProperty("password") String password, @JsonProperty("creditCard") String creditCard) {
        super(name, mail, password);
        this.creditCard = creditCard;
    }

    /***
     * Constructor for a customer that is not logged in yet
     * @param mail : the mail used to log in
     * @param password : the password used to log in
     */
    public CliCustomer(String mail, String password) {
        //We need to initialize the name to "default" because the backend will not accept a null or a blank value
        super("default", mail, password);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getMail() {
        return super.getMail();
    }

    public void setMail(String mail) {
        super.setMail(mail);
    }

    public String getPassword() {
        return super.getPassword();
    }

    public void setPassword(String password) {
        super.setPassword(password);
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "CliCustomer{" +
                "id=" + id +
                ", name='" + super.getName() + '\'' +
                ", mail='" + super.getMail() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", balance=" + balance +
                ", creditCard='" + creditCard + '\'' +
                '}';
    }
}
