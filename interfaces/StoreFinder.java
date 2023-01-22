package interfaces;

import POJO.Store;
import POJO.StoreOwner;

import java.util.Optional;
import java.util.UUID;

public interface StoreFinder {

        Optional<Store> findStoreOwnerByName(String name, String password);
        Optional<Store> findStoreOwnerById(UUID id, String password);
}
