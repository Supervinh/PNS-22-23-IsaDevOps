package cli.commands;

import cli.CliContext;
import cli.model.CliDashboard;
import cli.model.CliStoreOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class StoreOwnerCommands {
    public static final String BASE_URI = "/owner";

    private final RestTemplate restTemplate;

    private final CliContext cliContext;

    @Autowired
    public StoreOwnerCommands(RestTemplate restTemplate, CliContext cliContext) {
        this.restTemplate = restTemplate;
        this.cliContext = cliContext;
    }

    @ShellMethod("Login a store owner in the backend (loginOwner OWNER_MAIL OWNER_PASSWORD)")
    public CliStoreOwner loginOwner(String mail, String password) {
        if (cliContext.getLoggedInUser() != null) {
            System.out.println("You are already logged in as " + cliContext.getLoggedInUser().getName());
            return null;
        }
        CliStoreOwner res = restTemplate.postForObject(BASE_URI + "/loginOwner", new CliStoreOwner(mail, password), CliStoreOwner.class);
        cliContext.setLoggedInUser(res);
        return res;
    }

    @ShellMethod("Ask indicators regarding the fidelity program (dashboard STORE_NAME)")
    public CliDashboard dashboard(String storeName) {
        return restTemplate.postForObject(getUri() + "/dashboard", storeName, CliDashboard.class);
    }

    @ShellMethod("Delete account(deleteStoreOwner)")
    public void deleteStoreOwner() {
        if (cliContext.getLoggedInUser().getClass() != CliStoreOwner.class) {
            System.out.println("You are not a store owner");
            return;
        }
        restTemplate.delete(getUri() + "/deleteStoreOwner");
        cliContext.setLoggedInUser(null);
    }

    @ShellMethod("Delete store (deleteStore STORE_NAME)")
    public void deleteStore(String storeName) {
        if (!(cliContext.getLoggedInUser().getClass().equals(CliStoreOwner.class))) {
            System.out.println("You are not a store owner");
            return;
        }
        System.out.println(getUri() + "/deleteStore/" + storeName);
        restTemplate.delete(getUri() + "/deleteStore/" + storeName);
    }

    private String getUri() {
        return BASE_URI + "/" + cliContext.getLoggedInUser().getId();
    }
}
