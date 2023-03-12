package mfc.interfaces.explorer;

import mfc.pojo.Store;

import java.util.Optional;
import java.util.UUID;

public interface StoreFinder {
    Optional<Store> findStoreByName(String name);

    Optional<Store> findStoreById(UUID id);

    Optional<String[][]> findStoreOpeningHours(Store store);
}
