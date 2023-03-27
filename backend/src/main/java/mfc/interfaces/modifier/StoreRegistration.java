package mfc.interfaces.modifier;

import mfc.entities.Store;
import mfc.entities.StoreOwner;
import mfc.exceptions.AlreadyExistingStoreException;
import mfc.exceptions.NoStoreFoundException;

import java.util.List;

public interface StoreRegistration {
    Store register(String name, List<String> schedule, StoreOwner storeOwner) throws AlreadyExistingStoreException;

    Store delete(Store store) throws NoStoreFoundException;
}
