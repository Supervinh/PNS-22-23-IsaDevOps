package cli.commands;

import cli.CliContext;
import cli.model.CliAdmin;
import cli.model.CliStoreOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class AdminCommands {

    public static final String BASE_URI = "/admin";

    private final RestTemplate restTemplate;

    private final CliContext cliContext;

    @Autowired
    public AdminCommands(RestTemplate restTemplate, CliContext cliContext) {
        this.restTemplate = restTemplate;
        this.cliContext = cliContext;
    }

    @ShellMethod("Register an admin in the CoD backend (registerAdmin ADMIN_NAME ADMIN_MAIL ADMIN_PWD)")
    public CliAdmin registerAdmin(String name, String mail, String password) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return null;
        }
        if (!cliContext.getLoggedInUser().getClass().equals(CliAdmin.class)) {
            System.out.println("You are not an admin");
            return null;
        }
        return restTemplate.postForObject(BASE_URI + "/registerAdmin", new CliAdmin(name, mail, password), CliAdmin.class);
    }

    @ShellMethod("Login an admin in the CoD backend (loginAdmin ADMIN_MAIL ADMIN_PASSWORD)")
    public CliAdmin loginAdmin(String mail, String password) {
        if (cliContext.getLoggedInUser() != null) {
            System.out.println("You are already logged in as " + cliContext.getLoggedInUser().getName());
            return null;
        }
        CliAdmin res = restTemplate.postForObject(BASE_URI + "/loginAdmin", new CliAdmin(mail, password), CliAdmin.class);
        cliContext.setLoggedInUser(res);
        return res;
    }

    @ShellMethod("Register a store owner in the CoD backend (registerOwner OWNER_NAME OWNER_MAIL OWNER_PWD)")
    public CliStoreOwner registerOwner(String name, String mail, String password) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return null;
        }
        if (!cliContext.getLoggedInUser().getClass().equals(CliAdmin.class)) {
            System.out.println("You are not an admin");
            return null;
        }
        return restTemplate.postForObject("/owner/registerOwner", new CliStoreOwner(name, mail, password), CliStoreOwner.class);
    }

    @ShellMethod("Delete an admin account in the CoD backend (deleteAdmin)")
    public void deleteAdmin() {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return;
        }
        if (!cliContext.getLoggedInUser().getClass().equals(CliAdmin.class)) {
            System.out.println("You are not an admin");
            return;
        }
        restTemplate.delete(getUri() + "/deleteAdmin");
        cliContext.setLoggedInUser(null);
    }

    private String getUri() {
        return BASE_URI + "/" + cliContext.getLoggedInUser().getId();
    }
}
