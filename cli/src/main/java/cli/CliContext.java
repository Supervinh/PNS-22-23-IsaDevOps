package cli;

import cli.model.CliAdmin;
import cli.model.CliCustomer;
import cli.model.CliStore;
import cli.model.CliStoreOwner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CliContext {

    private final Map<String, CliCustomer> customers;
    private final Map<String, CliAdmin> admins;
    private final Map<String, CliStoreOwner> owners;

    public Map<String, CliCustomer> getCustomers() {
        return customers;
    }
    public Map<String, CliAdmin> getAdmins() {
        return admins;
    }
    public Map<String, CliStoreOwner> getOwners() {
        return owners;
    }

    public CliContext() {
        customers = new HashMap<>();
        admins = new HashMap<>();
        owners = new HashMap<>();
    }

    @Override
    public String toString() {
        return customers.keySet().stream()
                .map(key -> key + "=" + customers.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
    }

}
