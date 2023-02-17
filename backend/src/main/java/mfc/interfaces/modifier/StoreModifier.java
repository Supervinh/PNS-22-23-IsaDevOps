package mfc.interfaces.modifier;

import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.exceptions.CredentialsException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public interface StoreModifier {
    boolean updateOpeningHours(Store store, Map<LocalTime, LocalTime> openingHours, StoreOwner storeOwner) throws CredentialsException;
}
