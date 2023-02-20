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

    @ShellMethod("Register a customer in the CoD backend (register CUSTOMER_NAME CUSTOMER_MAIL CUSTOMER_PASSWORD CREDIT_CARD_NUMBER)")
    public CliCustomer register(String name, String mail, String password, @ShellOption (defaultValue = "null") String creditCard) {
        CliCustomer res = creditCard.equals("null") ?
                restTemplate.postForObject(BASE_URI + "/register", new CliCustomer(name, mail, password, "0000000000"), CliCustomer.class): //TODO trouver comment faire pour que le credit card soit null
                restTemplate.postForObject(BASE_URI + "/register", new CliCustomer(name, mail, password, creditCard), CliCustomer.class);
        cliContext.getCustomers().put(res.getName(), res);
        return res;
    }

    // Always use a POST request for login and not a GET request
    @ShellMethod("Login a customer in the CoD backend (login CUSTOMER_MAIL CUSTOMER_PASSWORD)")
    public CliCustomer login(String mail, String password) {
        return restTemplate.postForObject(BASE_URI + "/login", new CliCustomer(mail, password), CliCustomer.class);

    }

    @ShellMethod("List all customers")
    public String customers() {
        return cliContext.getCustomers().toString();
    }

}
