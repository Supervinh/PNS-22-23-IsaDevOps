package mfc.interfaces.modifier;

import mfc.POJO.Customer;
import mfc.interfaces.exceptions.AlreadyExistingAccountException;

public interface CustomerRegistration {
    Customer register(String mail, String name, String password) throws AlreadyExistingAccountException;

}
