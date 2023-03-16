package mfc.interfaces.modifier;

import mfc.exceptions.AlreadyExistingStoreException;
import mfc.pojo.Store;
import mfc.pojo.StoreOwner;

import java.util.List;

public interface StoreRegistration {
    Store register(String name, List<String> schedule , StoreOwner storeOwner) throws AlreadyExistingStoreException;
}
