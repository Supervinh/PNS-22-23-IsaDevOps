package mfc.interfaces.modifier;

import mfc.exceptions.CredentialsException;
import mfc.entities.Store;
import mfc.entities.StoreOwner;

import java.util.List;

public interface StoreModifier {
    boolean updateOpeningHours(Store store, List<String> schedule, StoreOwner storeOwner) throws CredentialsException;
}
