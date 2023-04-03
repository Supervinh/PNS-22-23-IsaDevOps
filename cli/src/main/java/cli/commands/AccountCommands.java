package cli.commands;

import cli.CliContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class AccountCommands {

    private final CliContext cliContext;

    @Autowired
    public AccountCommands(CliContext cliContext) {
        this.cliContext = cliContext;
    }

    @ShellMethod("Logout a account in the CoD backend (logout)")
    public void logout() {
        cliContext.setLoggedInUser(null);
    }

    @ShellMethod("Display the logged in user (who)")
    public String who() {
        return cliContext.getLoggedInUser() == null ? "" : cliContext.getLoggedInUser().toString();
    }

}
