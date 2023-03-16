package mfc.interfaces.modifier;

import mfc.exceptions.CredentialsException;
import mfc.pojo.Store;
import mfc.pojo.StoreOwner;

import java.util.List;

public interface StoreModifier {
    boolean updateOpeningHours(Store store, List<String> schedule, StoreOwner storeOwner) throws CredentialsException;
}
