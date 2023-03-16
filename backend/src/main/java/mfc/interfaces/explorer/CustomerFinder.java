package mfc.interfaces.explorer;

import mfc.pojo.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerFinder {
    Optional<Customer> findCustomerByMail(String mail);

    Optional<Customer> findCustomerById(Long id);

    Optional<Customer> findCustomerByName(String name);
}
