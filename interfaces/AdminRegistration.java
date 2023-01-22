package interfaces;

import POJO.Admin;
import interfaces.Exceptions.AlreadyExistingAccountException;

public interface AdminRegistration {
    Admin register(String mail, String password, Admin authorization) throws AlreadyExistingAccountException;
}
