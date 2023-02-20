package mfc.interfaces.modifier;

import mfc.POJO.Customer;
import mfc.exceptions.AlreadyExistingAccountException;

public interface CustomerRegistration {
//    Customer register(String mail, String name, String password) throws AlreadyExistingAccountException;

    Customer register(String name, String mail, String password) throws AlreadyExistingAccountException;
    Customer register(String name, String mail, String password, String creditCard) throws AlreadyExistingAccountException;

}
