package mfc.interfaces.modifier;

import mfc.POJO.Admin;
import mfc.exceptions.AlreadyExistingAccountException;

public interface AdminRegistration {
    Admin register(String mail, String password, Admin authorization) throws AlreadyExistingAccountException;
}

