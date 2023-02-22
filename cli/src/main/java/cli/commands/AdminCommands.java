package cli.commands;

import cli.CliContext;
import cli.model.CliAdmin;
import cli.model.CliCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class AdminCommands {

    public static final String BASE_URI = "/admin";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;

    @ShellMethod("Register an admin in the CoD backend (registerAdmin ADMIN_NAME ADMIN_MAIL ADMIN_PWD AUTH_MAIL, AUTH_PWD)")
    public CliAdmin registerAdmin(String name, String mail, String password, String authMail, String authPassword) {
        CliAdmin res = restTemplate.postForObject(BASE_URI + "/registerOwner", new CliAdmin(name,mail,password,authMail,authPassword), CliAdmin.class);
        cliContext.getAdmins().put(res.getName(), res);
        cliContext.getAdmins().put(res.getName(), res);
        return res;
    }
}
