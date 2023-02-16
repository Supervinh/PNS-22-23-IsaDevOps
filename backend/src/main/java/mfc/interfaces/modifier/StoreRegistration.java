package mfc.interfaces.modifier;

import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.exceptions.AlreadyExistingStoreException;

import java.util.Map;

public interface StoreRegistration {
    Store register(Map<String, String> openingHours, StoreOwner storeOwner, String name) throws AlreadyExistingStoreException;
}
