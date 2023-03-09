package cli.commands;

import cli.CliContext;
import cli.model.CliNotification;
import cli.model.CliPayoffIdentifier;
import cli.model.CliPayoffPurchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.isNull;

@Configuration
@EnableScheduling
@ShellComponent
public class PayoffCommands {

    public static final String BASE_URI = "/payoff";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;

    @ShellMethod("Add balance to account (claimPayoff STORE_NAME PAY_OFF)")
    public CliPayoffPurchase claimPayoff(String storeName, String payOff) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return null;
        }
        return restTemplate.postForObject(getUriForCustomer() + "/claimPayoff", new CliPayoffIdentifier(storeName, payOff), CliPayoffPurchase.class);
    }

    @ShellMethod("Get notifications for customer (getNotifications)")
    @Scheduled(fixedRate = 30000)
    public void getNotifications() {
        if (!isNull(cliContext.getLoggedInUser()))
            restTemplate.getForObject(getUriForCustomer() + "/getNotification", CliNotification.class);
    }

    private String getUriForCustomer() {
        return BASE_URI + "/" + cliContext.getLoggedInUser().getId();
    }
}
