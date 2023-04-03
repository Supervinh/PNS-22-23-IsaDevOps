package cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// A cli side class being equivalent to the backend CustomerDTO, in terms of attributes
// so that the automatic JSON (de-)/serialization will make the two compatible on each side
public class CliCustomer extends CliAccount {

    private double balance;
    private String creditCard;
    private String matriculation;
    private List<CliSurvey> surveysToAnswer;
    private List<CliStore> favoritesStores;
    private LocalDateTime lastConnexion;

    @JsonCreator
    public CliCustomer(@JsonProperty("name") String name, @JsonProperty("mail") String mail, @JsonProperty("password") String password, @JsonProperty("creditCard") String creditCard, @JsonProperty("matriculation") String matriculation) {
        super(name, mail, password);
        this.creditCard = creditCard;
        this.matriculation = matriculation;
        surveysToAnswer = new ArrayList<>();
        favoritesStores = new ArrayList<>();
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


    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getMatriculation() {
        return matriculation;
    }

    public void setMatriculation(String matriculation) {
        this.matriculation = matriculation;
    }

    public List<CliSurvey> getSurveysToAnswer() {
        return surveysToAnswer;
    }

    public void setSurveysToAnswer(List<CliSurvey> surveysToAnswer) {
        this.surveysToAnswer = surveysToAnswer;
    }

    public List<CliStore> getFavoritesStores() {
        return favoritesStores;
    }

    public void setFavoritesStores(List<CliStore> favoritesStores) {
        this.favoritesStores = favoritesStores;
    }

    public LocalDateTime getLastConnexion() {
        return lastConnexion;
    }

    public void setLastConnexion(LocalDateTime lastConnexion) {
        this.lastConnexion = lastConnexion;
    }

    @Override
    public String toString() {
        return "CliCustomer{" +
                "balance=" + balance +
                ", creditCard='" + creditCard + '\'' +
                ", matriculation='" + matriculation + '\'' +
                ", surveysToAnswer=" + surveysToAnswer +
                ", favoritesStores=" + favoritesStores +
                ", lastConnexion=" + lastConnexion +
                '}';
    }
}
