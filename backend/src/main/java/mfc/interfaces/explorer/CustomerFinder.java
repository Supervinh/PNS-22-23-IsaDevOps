package mfc.interfaces.explorer;

import mfc.POJO.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerFinder {
    Optional<Customer> findCustomerByMail(String mail);

    Optional<Customer> findCustomerById(UUID id);

    Optional<Customer> findCustomerByName(String name);
}
