package interfaces;

import POJO.Customer;
import interfaces.Exceptions.AlreadyExistingAccountException;

public interface CustomerRegistration {
    Customer register(String mail, String password) throws AlreadyExistingAccountException;

}
