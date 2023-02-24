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

    @ShellMethod("Shows avaible catalog to the customer(availableCatalog CUSTOMERNAME)")
    public CliCatalog availableCatalog() {
        return restTemplate.getForObject(getUriForCustomer() + "/availableCatalog", CliCatalog.class);
    }

    private String getUriForCustomer() {
        return BASE_URI + "/" + cliContext.getLoggedInUser().getId() + "/cat";
    }

}
