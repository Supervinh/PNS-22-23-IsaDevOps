package mfc.interfaces.modifier;

import mfc.entities.Customer;
import mfc.exceptions.AccountNotFoundException;
import mfc.exceptions.AlreadyExistingAccountException;

public interface CustomerRegistration {

    Customer register(String name, String mail, String password, String creditCard) throws AlreadyExistingAccountException;

    Customer delete(Customer customer) throws AccountNotFoundException;
}
