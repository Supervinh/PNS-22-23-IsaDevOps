package mfc.interfaces.modifier;

import mfc.POJO.Customer;
import mfc.interfaces.Exceptions.AlreadyExistingAccountException;

public interface CustomerRegistration {
    Customer register(String mail, String password) throws AlreadyExistingAccountException;

}
