package mfc.interfaces.modifier;

import mfc.exceptions.AlreadyExistingAccountException;
import mfc.entities.Admin;

public interface AdminRegistration {
    Admin registerAdmin(String name, String mail, String password) throws AlreadyExistingAccountException;
}

