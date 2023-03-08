package mfc.interfaces.modifier;

import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.exceptions.CredentialsException;

public interface StoreModifier {
    boolean updateOpeningHours(Store store, List<String> schedule, StoreOwner storeOwner) throws CredentialsException;
}
