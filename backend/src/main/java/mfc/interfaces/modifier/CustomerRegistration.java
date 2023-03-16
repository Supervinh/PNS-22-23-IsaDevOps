package mfc.interfaces.modifier;

import mfc.exceptions.AlreadyExistingAccountException;
import mfc.pojo.Customer;

public interface CustomerRegistration {
    Customer register(String name, String mail, String password) throws AlreadyExistingAccountException;
    Customer register(String name, String mail, String password, String creditCard) throws AlreadyExistingAccountException;

}
