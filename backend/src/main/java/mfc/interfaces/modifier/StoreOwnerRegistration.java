package mfc.interfaces.modifier;

import mfc.exceptions.AlreadyExistingAccountException;
import mfc.pojo.StoreOwner;

public interface StoreOwnerRegistration {
    StoreOwner registerStoreOwner(String name, String mail, String password) throws AlreadyExistingAccountException;

}
