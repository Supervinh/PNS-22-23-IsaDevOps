package mfc.interfaces.explorer;

import mfc.POJO.Store;

import java.util.Optional;
import java.util.UUID;

public interface StoreFinder {
    Optional<Store> findStoreByName(String name);

    Optional<Store> findStoreById(UUID id);

//    Optional<Map<LocalTime, LocalTime>> findStoreOpeningHours(Store store);
}
