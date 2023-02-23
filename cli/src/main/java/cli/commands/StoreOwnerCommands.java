package cli.commands;

import cli.CliContext;
import cli.model.CliStoreOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class StoreOwnerCommands {
    public static final String BASE_URI = "/admin";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;

    @ShellMethod("Register a store owner in the CoD backend (registerOwner OWNER_NAME OWNER_MAIL OWNER_PWD AUTH_MAIL, AUTH_PWD)")
    public CliStoreOwner registerOwner(String name, String mail, String password, String authMail, String authPassword) {
//        CliStoreOwner owner = new CliStoreOwner();
//        owner.setAuthorizationMail(authMail);
//        owner.setAuthorizationPassword(authPassword);
//        owner.setMail(mail);
//        owner.setPassword(password);
//        owner.setName(name);
        CliStoreOwner res = restTemplate.postForObject(BASE_URI + "/registerOwner", new CliStoreOwner(name, mail, password, authMail, authPassword), CliStoreOwner.class);
        cliContext.getOwners().put(res.getName(), res);
        return res;
    }
}
