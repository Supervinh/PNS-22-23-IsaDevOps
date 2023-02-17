package mfc.interfaces.modifier;

import mfc.POJO.Admin;
import mfc.exceptions.AlreadyExistingAccountException;

public interface AdminRegistration {
    Admin registerAdmin(String name, String mail, String password) throws AlreadyExistingAccountException;
}

