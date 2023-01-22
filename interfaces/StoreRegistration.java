package interfaces;

import POJO.Store;
import POJO.StoreOwner;
import interfaces.Exceptions.AlreadyExistingStoreException;

import java.util.Map;

public interface StoreRegistration {

    Store register(Map<String, String> openingHours, StoreOwner st, String name) throws AlreadyExistingStoreException;
}
