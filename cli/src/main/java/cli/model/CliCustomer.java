package cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

// A cli side class being equivalent to the backend CustomerDTO, in terms of attributes
// so that the automatic JSON (de-)/serialization will make the two compatible on each side
public class CliCustomer {

    private UUID id; // expected to be empty when POSTing the creation of Customer, and containing the UUID when returned
    private String name;
    private String mail;
    private String password;
    private double balance;
    private String creditCard;

    @JsonCreator
    public CliCustomer(@JsonProperty("name") String name, @JsonProperty("mail") String mail, @JsonProperty("password") String password, @JsonProperty("creditCard") String creditCard) {
        this.name = name;
        this.creditCard = creditCard;
        this.mail = mail;
        this.password = password;
    }

    /***
     * Constructor for a customer that is not logged in yet
     * @param mail : the mail used to log in
     * @param password : the password used to log in
     */
    public CliCustomer(String mail, String password) {
        //We need to initialize the name to "default" because the backend will not accept a null or a blank value
        this.name = "default";
        this.mail = mail;
        this.password = password;
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
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", creditCard='" + creditCard + '\'' +
                '}';
    }
}
