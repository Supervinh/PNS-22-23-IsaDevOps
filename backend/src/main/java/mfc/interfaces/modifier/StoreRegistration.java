package mfc.interfaces.modifier;

import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.exceptions.AlreadyExistingStoreException;

public interface StoreRegistration {
    Store register(/*Map<LocalTime, LocalTime> openingHours, */StoreOwner storeOwner, String name) throws AlreadyExistingStoreException;
}
