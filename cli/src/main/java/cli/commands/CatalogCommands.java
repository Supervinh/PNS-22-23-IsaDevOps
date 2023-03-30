package cli.commands;

import cli.CliContext;
import cli.model.*;
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

    @ShellMethod("Shows avaible catalog to the customer(availableCatalog)")
    public CliCatalog availableCatalog() {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return null;
        }
        return restTemplate.getForObject(getUri() + "/availableCatalog", CliCatalog.class);
    }

    @ShellMethod("Shows subset of the catalog matching the corresponding search filter(exploreCatalog SEARCH_QUERY)")
    public CliCatalog exploreCatalog(String search) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return null;
        }
        return restTemplate.postForObject(getUri() + "/exploreCatalog", search, CliCatalog.class);
    }

    @ShellMethod("Adds a payoff to the catalog(addPayoff PAYOFF_NAME COST POINT_COST STORE_NAME IS_VFP)")
    public CliPayoff addPayoff(String payoffName, double cost, int pointCost, String storeName, @ShellOption(arity = 1, defaultValue = "false") boolean isVfp) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return null;
        }
        if (!(cliContext.getLoggedInUser().getClass() == CliStoreOwner.class ||
                cliContext.getLoggedInUser().getClass() == CliAdmin.class)) {
            System.out.println("You are not logged in as a store owner or as an admin");
            return null;
        }

        CliPayoff cliPayoff = new CliPayoff(payoffName, cost, pointCost, storeName, isVfp);
        return restTemplate.postForObject(getUri() + "/addPayoff", cliPayoff, CliPayoff.class);
    }

    @ShellMethod("Deletes a payoff from the catalog(deletePayoff STORE_NAME PAYOFF_NAME)")
    public void deletePayoff(String storeName, String payoffName) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return;
        }
        if (!(cliContext.getLoggedInUser().getClass() == CliStoreOwner.class ||
                cliContext.getLoggedInUser().getClass() == CliAdmin.class)) {
            System.out.println("You are not logged in as a store owner or as an admin");
            return;
        }
        CliDeletePayoff payoff = new CliDeletePayoff(storeName, payoffName);
        restTemplate.postForObject(getUri() + "/deletePayoff/", payoff, Void.class);
    }

    //TODO editPayoffName available
    @ShellMethod("Edit a payoff(editPayoff STORE_NAME PAYOFF_NAME NEW_COST NEW_POINT_COST)")
    public CliPayoff editPayoff(String storeName, String payoffName, @ShellOption(defaultValue = "0") double newCost, @ShellOption(defaultValue = "0") int newPointCost, @ShellOption(arity = 1, defaultValue = "false") boolean isVfp) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return null;
        }
        if (!(cliContext.getLoggedInUser().getClass() == CliStoreOwner.class ||
                cliContext.getLoggedInUser().getClass() == CliAdmin.class)) {
            System.out.println("You are not logged in as a store owner or as an admin");
            return null;
        }
        CliPayoff payoff = new CliPayoff(payoffName, newCost, newPointCost, storeName, isVfp);
        return restTemplate.postForObject(getUri() + "/editPayoff/", payoff, CliPayoff.class);
    }


    private String getUri() {
        return BASE_URI + "/" + cliContext.getLoggedInUser().getId();
    }


}
