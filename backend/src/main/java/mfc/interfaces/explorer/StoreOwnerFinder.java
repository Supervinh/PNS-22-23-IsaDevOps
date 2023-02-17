package mfc.interfaces.explorer;

import mfc.POJO.StoreOwner;

import java.util.Optional;
import java.util.UUID;

public interface StoreOwnerFinder {
    Optional<StoreOwner> findStoreOwnerByMail(String mail);

    Optional<StoreOwner> findStoreOwnerByMailAndPassword(String mail, String password);

    Optional<StoreOwner> findStoreOwnerById(UUID id);
}
