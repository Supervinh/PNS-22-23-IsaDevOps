package mfc.interfaces.modifier;

import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.exceptions.AlreadyExistingStoreException;

import java.util.List;

public interface StoreRegistration {
    Store register(String name, String[][] schedule , StoreOwner storeOwner) throws AlreadyExistingStoreException;
}
