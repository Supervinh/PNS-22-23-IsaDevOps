package mfc.controllers.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomerDTO {

    private Long id; // expected to be empty when POSTing the creation of Customer, and containing the UUID when returned

    @NotBlank(message = "name should not be blank")
    private String name;
    @Email(message = "mail should be a valid email")
    @NotBlank(message = "mail should not be blank")
    private String mail;
    @NotBlank(message = "password should not be blank")
    private String password;
    private List<StoreDTO> favoritesStores;
    private List<SurveyDTO> surveysToAnswer;
    private String creditCard;
    private LocalDateTime lastConnexion;

    private String matriculation;

    private double balance;


    public CustomerDTO(Long id, String name, String mail, String password, String creditCard, String matriculation) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.creditCard = creditCard;
        this.matriculation = matriculation;
        balance = 0;
        surveysToAnswer = new ArrayList<>();
        favoritesStores = new ArrayList<>();
    }


    public Long getId() {
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


    public void setCreditCard(@Pattern(regexp = "\\d{10}+", message = "credit card should be exactly 10 digits") String creditCard) {
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

    public String getMatriculation() {
        return matriculation;
    }

    public void setMatriculation(String matriculation) {
        this.matriculation = matriculation;
    }

    public List<SurveyDTO> getSurveysToAnswer() {
        return surveysToAnswer;
    }

    public void setSurveysToAnswer(List<SurveyDTO> surveysToAnswer) {
        this.surveysToAnswer = surveysToAnswer;
    }

    public List<StoreDTO> getFavoritesStores() {
        return favoritesStores;
    }

    public void setFavoritesStores(List<StoreDTO> favoritesStores) {
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
        return "CustomerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", favoritesStores=" + favoritesStores +
                ", surveysToAnswer=" + surveysToAnswer +
                ", creditCard='" + creditCard + '\'' +
                ", lastConnexion=" + lastConnexion +
                ", matriculation='" + matriculation + '\'' +
                ", balance=" + balance +
                '}';
    }
}
