package mfc.interfaces;

import mfc.POJO.Admin;
import mfc.interfaces.Exceptions.AlreadyExistingAccountException;

public interface AdminRegistration {
    Admin register(String mail, String password, Admin authorization) throws AlreadyExistingAccountException;
}

