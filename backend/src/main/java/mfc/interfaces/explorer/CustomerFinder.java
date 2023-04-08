package mfc.interfaces.explorer;

import mfc.entities.Customer;
import mfc.exceptions.AccountNotFoundException;
import mfc.exceptions.CredentialsException;

import java.util.Optional;

public interface CustomerFinder {
    Optional<Customer> findCustomerByMail(String mail);

    Optional<Customer> findCustomerById(Long id);

    Optional<Customer> findCustomerByName(String name);

    Optional<Customer> findCustomerAtConnexion(String mail, String password) throws CredentialsException, AccountNotFoundException;
}
