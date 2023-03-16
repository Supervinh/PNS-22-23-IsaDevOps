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

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;

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
        if (cliContext.getLoggedInUser() != null) {
            System.out.println("You are already logged in as " + cliContext.getLoggedInUser().getName());
            return null;
        }
        return restTemplate.postForObject(getUri() + "/dashboard", storeName, CliDashboard.class);
    }

    private String getUri() {
        return BASE_URI + "/" + cliContext.getLoggedInUser().getId();
    }
}
