package cli.commands;

import cli.CliContext;
import cli.model.CliCatalog;
import cli.model.CliPayoff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class CatalogCommands {

    public static final String BASE_URI = "/catalog";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;

    @ShellMethod("Shows avaible catalog to the customer(availableCatalog CUSTOMER_NAME)")
    public CliCatalog availableCatalog(String name) {
        return restTemplate.getForObject(getUriForCustomer(name) + "/availableCatalog", CliCatalog.class);
    }

    @ShellMethod("Shows subset of the catalog matching the corresponding search filter(exploreCatalog CUSTOMER_NAME SEARCH_QUERY)")
    public CliCatalog exploreCatalog(String name, String search) {
        return restTemplate.postForObject(getUriForCustomer(name) + "/exploreCatalog", search, CliCatalog.class);
    }

    @ShellMethod("Adds a payoff to the catalog(addPayoff STORE_OWNER_NAME PAYOFF_NAME COST POINT_COST STORE_NAME )")
    public CliPayoff addPayoff(String storeOwner_name, String payoff_name, double cost, int pointCost, String store_name) {
        CliPayoff cliPayoff = new CliPayoff(payoff_name, cost, pointCost, store_name);
        return restTemplate.postForObject(getUriForStoreOwner(storeOwner_name) + "/addPayoff", cliPayoff, CliPayoff.class);
    }

    @ShellMethod("Deletes a payoff from the catalog(deletePayoff STORE_OWNER_NAME STORE_NAME PAYOFF_NAME)")
    public Boolean deletePayoff(String storeOwner_name, String storeName, String payoffName) { //TODO refaire un dto pour le delete. Return un boolean ou une payoff ?
        CliPayoff payoff = new CliPayoff(payoffName, 0, 0, storeName);
        return restTemplate.postForObject(getUriForStoreOwner(storeOwner_name) + "/deletePayoff/", payoff, Boolean.class);
    }

    //TODO editPayoffName available
    @ShellMethod("Edit a payoff(editPayoff STORE_OWNER_NAME STORE_NAME PAYOFF_NAME NEW_COST NEW_POINT_COST)")
    public CliPayoff editPayoff(String storeOwner_name, String storeName, String payoffName, @ShellOption(defaultValue = "0") double newCost, @ShellOption(defaultValue = "0") int newPointCost) {
        CliPayoff payoff = new CliPayoff(payoffName, newCost, newPointCost, storeName);
        return restTemplate.postForObject(getUriForStoreOwner(storeOwner_name) + "/editPayoff/", payoff, CliPayoff.class);
    }

    private String getUriForCustomer(String name) {
        return BASE_URI + "/" + cliContext.getCustomers().get(name).getId() + "/cat";
    }

    private String getUriForStoreOwner(String name) {
        return BASE_URI + "/" + cliContext.getOwners().get(name).getId() + "/cat";
    }
}
