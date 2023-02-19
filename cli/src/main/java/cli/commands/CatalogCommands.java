package cli.commands;

import cli.CliContext;
import cli.model.CliCatalog;
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


    private String getUriForCustomer(String name) {
        return BASE_URI + "/" + cliContext.getCustomers().get(name).getId() + "/cat";
    }

}
