package cli.commands;

import cli.CliContext;
import cli.model.CliCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class CustomerCommands {

    public static final String BASE_URI = "/customers";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;

    @ShellMethod("Register a customer in the CoD backend (registerCustomer CUSTOMER_NAME CUSTOMER_MAIL CUSTOMER_PASSWORD CREDIT_CARD_NUMBER)")
    public CliCustomer registerCustomer(String name, String mail, String password, @ShellOption(defaultValue = "") String creditCard) {
        return restTemplate.postForObject(BASE_URI + "/registerCustomer", new CliCustomer(name, mail, password, creditCard,"null"), CliCustomer.class);
    }

    // Always use a POST request for login and not a GET request
    @ShellMethod("Login a customer in the CoD backend (login CUSTOMER_MAIL CUSTOMER_PASSWORD)")
    public CliCustomer loginCustomer(String mail, String password) {
        if (cliContext.getLoggedInUser() != null) {
            System.out.println("You are already logged in as " + cliContext.getLoggedInUser().getName());
            return null;
        }
        CliCustomer res = restTemplate.postForObject(BASE_URI + "/loginCustomer", new CliCustomer(mail, password), CliCustomer.class);
        cliContext.setLoggedInUser(res);
        return res;
    }

    @ShellMethod("Modify the credit card of the logged in user (modifyCreditCard CREDIT_CARD_NUMBER)")
    public CliCustomer modifyCreditCard(String creditCard) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return null;
        }
        CliCustomer res = restTemplate.postForObject(getUriForCustomer() + "/modifyCreditCard",
                creditCard,
                CliCustomer.class);
        cliContext.setLoggedInUser(res);
        return res;
    }

    @ShellMethod("Modify the credit card of the logged in user (modifyCreditCard CREDIT_CARD_NUMBER)")
    public CliCustomer modifyMatriculation(String matriculation) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return null;
        }
        CliCustomer res = restTemplate.postForObject(getUriForCustomer() + "/modifyMatriculation",
                matriculation,
                CliCustomer.class);
        cliContext.setLoggedInUser(res);
        return res;
    }


    private String getUriForCustomer() {
        return BASE_URI + "/" + cliContext.getLoggedInUser().getId();
    }
    
    

}
