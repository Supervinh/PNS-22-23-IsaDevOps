package mfc.interfaces.explorer;

import mfc.entities.Store;

import java.util.Optional;

public interface StoreFinder {
    Optional<Store> findStoreByName(String name);
}
