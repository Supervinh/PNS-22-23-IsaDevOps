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

    private final RestTemplate restTemplate;

    private final CliContext cliContext;

    @Autowired
    public CustomerCommands(RestTemplate restTemplate, CliContext cliContext) {
        this.restTemplate = restTemplate;
        this.cliContext = cliContext;
    }

    @ShellMethod("Register a customer in the CoD backend (registerCustomer CUSTOMER_NAME CUSTOMER_MAIL CUSTOMER_PASSWORD CREDIT_CARD_NUMBER)")
    public CliCustomer registerCustomer(String name, String mail, String password, @ShellOption(defaultValue = "") String creditCard) {
        return restTemplate.postForObject(BASE_URI + "/registerCustomer", new CliCustomer(name, mail, password, creditCard, "null"), CliCustomer.class);
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

    @ShellMethod("Add a favorite store to the logged in user (addFavoriteStore STORE_NAME)")
    public CliCustomer addFavoriteStore(String storeName) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return null;
        }
        if (cliContext.getLoggedInUser().getClass() != CliCustomer.class) {
            System.out.println("You are not a customer");
            return null;
        }
        return restTemplate.postForObject(getUriForCustomer() + "/addFavoriteStore", storeName, CliCustomer.class);
    }

    @ShellMethod("Remove a favorite store from the logged in user (removeFavoriteStore STORE_NAME)")
    public CliCustomer removeFavoriteStore(String storeName) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return null;
        }
        if (cliContext.getLoggedInUser().getClass() != CliCustomer.class) {
            System.out.println("You are not a customer");
            return null;
        }
        return restTemplate.postForObject(getUriForCustomer() + "/removeFavoriteStore", storeName, CliCustomer.class);
    }

    @ShellMethod("Modify the credit card of the logged in user (modifyCreditCard CREDIT_CARD_NUMBER)")
    public CliCustomer modifyCreditCard(String creditCard) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return null;
        }
        if (cliContext.getLoggedInUser().getClass() != CliCustomer.class) {
            System.out.println("You are not a customer");
            return null;
        }
        CliCustomer res = restTemplate.postForObject(getUriForCustomer() + "/modifyCreditCard",
                creditCard,
                CliCustomer.class);
        cliContext.setLoggedInUser(res);
        return res;
    }

    @ShellMethod("Modify the matriculation of the logged user (modifyMatriculation MATRICULATION)")
    public CliCustomer modifyMatriculation(String matriculation) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return null;
        }
        if (cliContext.getLoggedInUser().getClass() != CliCustomer.class) {
            System.out.println("You are not a customer");
            return null;
        }
        CliCustomer res = restTemplate.postForObject(getUriForCustomer() + "/modifyMatriculation",
                matriculation,
                CliCustomer.class);
        cliContext.setLoggedInUser(res);
        return res;
    }

    @ShellMethod("Add balance to account (refill AMOUNT)")//TODO add credit card
    public CliCustomer refill(double amount) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return null;
        }
        if (cliContext.getLoggedInUser().getClass() != CliCustomer.class) {
            System.out.println("You are not a customer");
            return null;
        }
        return restTemplate.postForObject(getUriForCustomer() + "/refill", amount, CliCustomer.class);
    }

    @ShellMethod("Delete customer account (deleteCustomer)")
    public void deleteCustomer() {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return;
        }
        if (!cliContext.getLoggedInUser().getClass().equals(CliCustomer.class)) {
            System.out.println("You are not a customer");
            return;
        }
        restTemplate.delete(getUriForCustomer() + "/deleteCustomer");
        cliContext.setLoggedInUser(null);
    }

    private String getUriForCustomer() {
        return BASE_URI + "/" + cliContext.getLoggedInUser().getId();
    }
}
