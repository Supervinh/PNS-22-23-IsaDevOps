package mfc.interfaces.modifier;

import mfc.entities.Admin;
import mfc.exceptions.AccountNotFoundException;
import mfc.exceptions.AlreadyExistingAccountException;

public interface AdminRegistration {
    Admin registerAdmin(String name, String mail, String password) throws AlreadyExistingAccountException;

    Admin delete(Admin admin) throws AccountNotFoundException;
}

