package interfaces;

import POJO.Store;
import POJO.StoreOwner;
import interfaces.Exceptions.CredentialsException;

import java.util.Map;

public interface StoreModifier {
    boolean updateOpeningHours(Store store, Map<String, String> openingHours, StoreOwner storeOwner) throws CredentialsException;
}
