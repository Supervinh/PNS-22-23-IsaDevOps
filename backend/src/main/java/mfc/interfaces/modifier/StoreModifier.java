package mfc.interfaces.modifier;

import mfc.entities.Store;
import mfc.entities.StoreOwner;
import mfc.exceptions.CredentialsException;

import java.util.Map;

public interface StoreModifier {
    boolean updateOpeningHours(Store store, Map<String, String> schedule, StoreOwner storeOwner) throws CredentialsException;

}
