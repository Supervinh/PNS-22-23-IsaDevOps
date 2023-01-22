package interfaces;

import POJO.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerFinder {

    Optional<Customer> findCustomerByMail(String mail, String password);
    Optional<Customer> findCustomerById(UUID id, String password);
}
