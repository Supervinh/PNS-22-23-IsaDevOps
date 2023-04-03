package mfc.interfaces.modifier;

import mfc.entities.StoreOwner;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.exceptions.NoCorrespongingAccountException;

public interface StoreOwnerRegistration {
    StoreOwner registerStoreOwner(String name, String mail, String password) throws AlreadyExistingAccountException;

    StoreOwner delete(StoreOwner owner) throws NoCorrespongingAccountException;
}
