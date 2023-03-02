package cli.commands;

import cli.CliContext;
import cli.model.CliAdmin;
import cli.model.CliCustomer;
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

    @ShellMethod("Login a store owner in the CoD backend (loginOwner OWNER_MAIL OWNER_PASSWORD)")
    public CliCustomer loginOwner(String mail, String password) {
        if (cliContext.getLoggedInUser() != null) {
            System.out.println("You are already logged in as " + cliContext.getLoggedInUser().getName());
            return null;
        }
        CliCustomer res = restTemplate.postForObject(BASE_URI + "/loginOwner", new CliStoreOwner(mail, password), CliCustomer.class);
        cliContext.setLoggedInUser(res);
        return res;
    }
}
