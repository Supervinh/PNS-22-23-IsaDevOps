package mfc.interfaces.explorer;

import mfc.POJO.Store;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreFinder {
    Optional<Store> findStoreByName(String name);
    Optional<Store> findStoreById(Long id);
}
