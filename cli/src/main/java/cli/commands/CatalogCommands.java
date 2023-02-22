package cli.commands;

import cli.CliContext;
import cli.model.CliCatalog;
import cli.model.CliPayoff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
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
        CliPayoff cliPayoff = new CliPayoff(payoff_name, cost, pointCost, cliContext.getStores().get(store_name));
        return restTemplate.postForObject(getUriForStoreOwner(storeOwner_name) + "/addPayoff", cliPayoff, CliPayoff.class);
    }

    @ShellMethod("Deletes a payoff from the catalog(deletePayoff STORE_OWNER_NAME PAYOFF)")
    public CliPayoff deletePayoff(String name, CliPayoff payoff) {
        return restTemplate.postForObject(getUriForStoreOwner(name) + "/deletePayoff/", payoff, CliPayoff.class);
    }

    private String getUriForCustomer(String name) {
        return BASE_URI + "/" + cliContext.getCustomers().get(name).getId() + "/cat";
    }

    private String getUriForStoreOwner(String name) {
        return BASE_URI + "/" + cliContext.getStoreOwners().get(name).getId() + "/cat";
    }
}
