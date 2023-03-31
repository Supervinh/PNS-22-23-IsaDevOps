package cli.commands;

import cli.CliContext;
import cli.model.CliPurchase;
import cli.model.CliStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@ShellComponent
public class StoreCommands {

    public static final String BASE_URI = "/store";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;

    @ShellMethod("Register a store in the CoD backend(registerStore STORE_NAME OPENING_TIME CLOSING_TIME)")
    public CliStore registerStore(String name, String... horaires) {
        HashMap<String, String> schedule = new HashMap<>();
//        List<String> sch = new ArrayList<>(14);
//        for (int i = 0; i < 7; i++) {
//            sch.add(opening);
//            sch.add(closing);
//        }
        return restTemplate.postForObject(getUri() + "/register", new CliStore(name, schedule), CliStore.class);
    }

    @ShellMethod("Register a purchase from a customer(addPurchase STORE_NAME CUSTOMER_EMAIL COST INTERNAL_ACCOUNT)")
    public CliPurchase addPurchase(String storeName, String customerEmail, int cost, @ShellOption(defaultValue = "false") boolean internalAccount) {
        return restTemplate.postForObject(getUri() + "/addPurchase", new CliPurchase(customerEmail, cost, storeName, internalAccount), CliPurchase.class);
    }

    private String getUri() {
        return BASE_URI + "/" + cliContext.getLoggedInUser().getId();
    }
}
