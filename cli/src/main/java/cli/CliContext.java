package cli;

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
    private final Map<String, CliStoreOwner> storeOwners;
    private final Map<String, CliStore> stores;

    public Map<String, CliCustomer> getCustomers() {
        return customers;
    }

    public CliContext() {
        customers = new HashMap<>();
        storeOwners = new HashMap<>();
        stores = new HashMap<>();
    }

    public Map<String, CliStoreOwner> getStoreOwners() {
        return storeOwners;
    }

    public Map<String, CliStore> getStores() {
        return stores;
    }

    @Override
    public String toString() {
        return customers.keySet().stream()
                .map(key -> key + "=" + customers.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
    }

}
