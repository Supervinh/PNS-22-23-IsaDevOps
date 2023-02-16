package mfc.interfaces.modifier;

import mfc.POJO.Admin;
import mfc.POJO.StoreOwner;
import mfc.interfaces.exceptions.AlreadyExistingAccountException;

public interface StoreOwnerRegistration {
    StoreOwner register(String mail, String password, Admin authorization) throws AlreadyExistingAccountException;

}
