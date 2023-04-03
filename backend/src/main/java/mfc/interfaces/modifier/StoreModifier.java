package mfc.interfaces.modifier;

import mfc.entities.Store;
import mfc.entities.StoreOwner;
import mfc.exceptions.AlreadyExistingStoreException;
import mfc.exceptions.NoStoreFoundException;
import mfc.exceptions.StoreNotFoundException;

import java.util.Map;

public interface StoreModifier {

    Store updateOpeningHours(Store store, Map<String, String> schedule) throws StoreNotFoundException;

    Store register(String name, Map<String, String> schedule, StoreOwner storeOwner) throws AlreadyExistingStoreException;

    Store delete(Store store) throws NoStoreFoundException;

    boolean deleteStores(StoreOwner store);

}
