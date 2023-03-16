package mfc.interfaces.explorer;

import mfc.entities.Customer;

import java.util.Optional;

public interface CustomerFinder {
    Optional<Customer> findCustomerByMail(String mail);

    Optional<Customer> findCustomerById(Long id);

    Optional<Customer> findCustomerByName(String name);
}
