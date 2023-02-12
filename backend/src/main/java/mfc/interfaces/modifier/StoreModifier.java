package mfc.interfaces.modifier;

import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.interfaces.Exceptions.CredentialsException;

import java.util.Map;

public interface StoreModifier {
    boolean updateOpeningHours(Store store, Map<String, String> openingHours, StoreOwner storeOwner) throws CredentialsException;
}
