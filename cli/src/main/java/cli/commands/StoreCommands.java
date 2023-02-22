package cli.commands;

import cli.CliContext;
import cli.model.CliStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

public class StoreCommands {

    public static final String BASE_URI = "/store";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;

    @ShellMethod("Register a store in the CoD backend(registerStore STORE_NAME OPENING_HOUR OPENING_MINUTE CLOSING_HOUR CLOSING_MINUTE OWNER_NAME)")
    public CliStore registerStore(String name, int openingHour, int openingMinute, int closingHour, int closingminute, String ownerName ) {
        String opening = openingHour+"h"+openingMinute+"m";
        String closing = closingHour+"h"+closingminute+"m";
        String[][] sch = new String[7][2];
        for (int i = 0; i < 7; i++) {
            sch[i][0] = opening;
            sch[i][1] = closing;
        }
        CliStore res = restTemplate.postForObject(BASE_URI + "/register", new CliStore(name, sch, ownerName), CliStore.class);
        cliContext.getStores().put(res.getName(), res);
        return res;
    }

}
