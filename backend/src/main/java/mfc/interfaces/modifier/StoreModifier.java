package mfc.interfaces.modifier;

import mfc.POJO.Schedule;
import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.exceptions.CredentialsException;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface StoreModifier {
    boolean updateOpeningHours(Store store, List<Schedule> schedule, StoreOwner storeOwner) throws CredentialsException;
}
