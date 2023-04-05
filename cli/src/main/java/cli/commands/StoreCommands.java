package cli.commands;

import cli.CliContext;
import cli.model.CliPurchase;
import cli.model.CliStore;
import cli.model.CliStoreOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@ShellComponent
public class StoreCommands {

    public static final String BASE_URI = "/store";

    private final RestTemplate restTemplate;

    private final CliContext cliContext;

    @Autowired
    public StoreCommands(RestTemplate restTemplate, CliContext cliContext) {
        this.restTemplate = restTemplate;
        this.cliContext = cliContext;
    }

    @ShellMethod("Register a store in the CoD backend(registerStore STORE_NAME)")
    public CliStore registerStore(String name) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You must be logged in to register a store");
            return null;
        }
        if (cliContext.getLoggedInUser().getClass() != CliStoreOwner.class) {
            System.out.println("You must be logged as an store owner to register a store");
            return null;
        }
        HashMap<String, String> schedule = new HashMap<>();
        return restTemplate.postForObject(getUri() + "/register", new CliStore(name, schedule), CliStore.class);
    }

    @ShellMethod("Modify the schedule of a store(modifySchedule STORE_NAME OPENING CLOSING)")
    public CliStore modifySchedule(String name, String day, String opening, String closing) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You must be logged in to register a store");
            return null;
        }
        if (cliContext.getLoggedInUser().getClass() != CliStoreOwner.class) {
            System.out.println("You must be logged as an store owner to register a store");
            return null;
        }
        HashMap<String, String> schedule = new HashMap<>();
        schedule.put(day + "0", opening);
        schedule.put(day + "1", closing);
        return restTemplate.postForObject(getUri() + "/modifySchedule", new CliStore(name, schedule), CliStore.class);
    }

    @ShellMethod("Register a purchase from a customer(addPurchase STORE_NAME CUSTOMER_EMAIL COST INTERNAL_ACCOUNT)")
    public CliPurchase addPurchase(String storeName, String customerEmail, int cost, @ShellOption(defaultValue = "false") boolean internalAccount) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You must be logged in to register a store");
            return null;
        }
        if (cliContext.getLoggedInUser().getClass() != CliStoreOwner.class) {
            System.out.println("You must be logged as an store owner to register a store");
            return null;
        }
        return restTemplate.postForObject(getUri() + "/addPurchase", new CliPurchase(customerEmail, cost, storeName, internalAccount), CliPurchase.class);
    }

    private String getUri() {
        return BASE_URI + "/" + cliContext.getLoggedInUser().getId();
    }
}
