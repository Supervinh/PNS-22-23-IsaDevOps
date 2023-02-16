package mfc.interfaces.explorer;

import mfc.POJO.Customer;
import mfc.POJO.Store;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerFinder {
    Optional<Customer> findCustomerByMail(String mail);

    Optional<Customer> findCustomerById(UUID id);
}
