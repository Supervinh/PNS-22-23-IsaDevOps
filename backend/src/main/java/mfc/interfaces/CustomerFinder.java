package mfc.interfaces;

import mfc.POJO.Customer;
import mfc.POJO.Store;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerFinder {
    Optional<Customer> findCustomerByMail(String mail, String password);

    Optional<List<String>> findCustomersMailByStore(Store store);

    Optional<Customer> findCustomerById(UUID id);
}
