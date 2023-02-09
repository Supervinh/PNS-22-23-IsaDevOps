package mfc.interfaces;

import mfc.POJO.StoreOwner;

import java.util.Optional;
import java.util.UUID;

public interface StoreOwnerFinder {
    Optional<StoreOwner> findStoreOwnerByMail(String mail, String password);

    Optional<StoreOwner> findStoreOwnerById(UUID id, String password);
}
