package cli.commands;

import cli.CliContext;
import cli.model.CliPayoffIdentifier;
import cli.model.CliPayoffPurchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class PayoffCommands {

    public static final String BASE_URI = "/payoff";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;

    @ShellMethod("Add balance to account (claimPayoff STORE_NAME PAY_OFF)")
    public CliPayoffPurchase claimPayoff(String storeName, String payOff) {
        return restTemplate.postForObject(getUriForCustomer() + "/claimPayoff", new CliPayoffIdentifier(storeName, payOff), CliPayoffPurchase.class);
    }

    private String getUriForCustomer() {
        return BASE_URI + "/" + cliContext.getLoggedInUser().getId();
    }
}
