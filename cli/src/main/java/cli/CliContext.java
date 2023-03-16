package cli;

import cli.model.CliAccount;
import org.springframework.stereotype.Component;

@Component
public class CliContext {

    private CliAccount loggedInUser;

    public CliContext() {
        loggedInUser = null;
    }

    public CliAccount getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(CliAccount user) {
        this.loggedInUser = user;
    }

    @Override
    public String toString() {
        return "CliContext{" +
                "userLoggedIn=" + loggedInUser +
                '}';
    }
}
