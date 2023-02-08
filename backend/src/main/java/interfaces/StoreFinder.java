package interfaces;

import POJO.Store;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface StoreFinder {
    Optional<Store> findStoreByName(String name);

    Optional<Store> findStoreById(UUID id);

    Optional<Map<String, String>> findOpeningHoursOfStore(Store store);
}
