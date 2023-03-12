package mfc.interfaces.modifier;

import mfc.exceptions.AlreadyExistingStoreException;
import mfc.pojo.Store;
import mfc.pojo.StoreOwner;

public interface StoreRegistration {
    Store register(String name, String[][] schedule, StoreOwner storeOwner) throws AlreadyExistingStoreException;
}
