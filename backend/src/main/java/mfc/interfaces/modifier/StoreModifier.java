package mfc.interfaces.modifier;

import mfc.exceptions.CredentialsException;
import mfc.pojo.Store;
import mfc.pojo.StoreOwner;

public interface StoreModifier {
    boolean updateOpeningHours(Store store, String[][] schedule, StoreOwner storeOwner) throws CredentialsException;
}
