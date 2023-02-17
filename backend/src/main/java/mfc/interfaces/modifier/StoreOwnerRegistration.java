package mfc.interfaces.modifier;

import mfc.POJO.Admin;
import mfc.POJO.StoreOwner;
import mfc.exceptions.AlreadyExistingAccountException;

public interface StoreOwnerRegistration {
    StoreOwner registerStoreOwner(String name, String mail, String password) throws AlreadyExistingAccountException;

}
