package cli;

import cli.model.CliAccount;
import cli.model.CliCustomer;
import cli.model.CliStore;
import cli.model.CliStoreOwner;
import org.springframework.stereotype.Component;

@Component
public class CliContext {

    private CliAccount loggedInUser;

    public CliAccount getLoggedInUser() {
        return loggedInUser;
    }

    public CliContext() {
        loggedInUser = null;
    }

    @Override
    public String toString() {
        return "CliContext{" +
                "userLoggedIn=" + loggedInUser +
                '}';
    }

    public void setLoggedInUser(CliCustomer user) {
        this.loggedInUser = user;
    }
}
