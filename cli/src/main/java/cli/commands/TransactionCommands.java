package cli.commands;

import cli.CliContext;
import cli.model.CliCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class TransactionCommands {

    public static final String BASE_URI = "/customers";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;

    @ShellMethod("Add balance to account (refill CUSTOMER_NAME AMOUNT)")
    public CliCustomer refill(String name, double amount) {
        return restTemplate.postForObject(getUriForCustomer(name) + "/refill", amount/*new CliPayment(amount)*/, CliCustomer.class);
    }

    private String getUriForCustomer(String name) {
        return BASE_URI + "/" + cliContext.getLoggedInUser().getId() + "/transactions";
    }
}
