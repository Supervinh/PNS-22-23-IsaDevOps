package interfaces;

import POJO.Customer;
import POJO.Store;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerFinder {
    Optional<Customer> findCustomerByMail(String mail, String password);

    Optional<List<String>> findCustomersMailByStore(Store store);

    Optional<Customer> findCustomerById(UUID id, String password);
}
