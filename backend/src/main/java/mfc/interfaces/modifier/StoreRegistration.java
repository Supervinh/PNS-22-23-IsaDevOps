package mfc.interfaces.modifier;

import mfc.exceptions.AlreadyExistingStoreException;
import mfc.entities.Store;
import mfc.entities.StoreOwner;

import java.util.List;

public interface StoreRegistration {
    Store register(String name, List<String> schedule , StoreOwner storeOwner) throws AlreadyExistingStoreException;
}
