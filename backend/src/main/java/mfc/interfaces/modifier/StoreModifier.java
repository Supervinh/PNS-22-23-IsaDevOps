package mfc.interfaces.modifier;

import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.exceptions.CredentialsException;

import java.util.List;

public interface StoreModifier {
    boolean updateOpeningHours(Store store, String[][] schedule, StoreOwner storeOwner) throws CredentialsException;
}
