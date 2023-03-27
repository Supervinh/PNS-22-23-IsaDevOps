package cli.commands;

import cli.CliContext;
import cli.model.CliPurchase;
import cli.model.CliStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@ShellComponent
public class StoreCommands {

    public static final String BASE_URI = "/store";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;

    //TODO string directs pour les horaires
    //sauf si verif de la validité des horaires e.g 24h00m
    @ShellMethod("Register a store in the CoD backend(registerStore STORE_NAME OPENING_HOUR OPENING_MINUTE CLOSING_HOUR CLOSING_MINUTE OWNER_NAME)")
    public CliStore registerStore(String name, int openingHour, int openingMinute, int closingHour, int closingminute, String ownerName) {
        String opening = openingHour + "h" + openingMinute + "m";
        String closing = closingHour + "h" + closingminute + "m";
        List<String> sch = new ArrayList<>(14);
        for (int i = 0; i < 7; i++) {
            sch.add(opening);
            sch.add(closing);
        }
        return restTemplate.postForObject(BASE_URI + "/register", new CliStore(name, sch, ownerName), CliStore.class);
    }

    @ShellMethod("Register a purchase from a customer(addPurchase STORE_NAME CUSTOMER_EMAIL COST INTERNAL_ACCOUNT)")
    public CliPurchase addPurchase(String storeName, String customerEmail, int cost, @ShellOption(defaultValue = "false") boolean internalAccount) {
        return restTemplate.postForObject(BASE_URI + "/addPurchase", new CliPurchase(customerEmail, cost, storeName, internalAccount), CliPurchase.class);
    }
}
