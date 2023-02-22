package mfc.interfaces.modifier;

import mfc.POJO.Schedule;
import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.exceptions.AlreadyExistingStoreException;

import java.time.LocalTime;
import java.util.List;

public interface StoreRegistration {
    Store register(String name, List<Schedule> schedule , StoreOwner storeOwner) throws AlreadyExistingStoreException;
}
