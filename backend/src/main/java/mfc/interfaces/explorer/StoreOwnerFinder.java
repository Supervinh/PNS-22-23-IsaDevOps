package mfc.interfaces.explorer;

import mfc.entities.StoreOwner;

import java.util.Optional;

public interface StoreOwnerFinder {
    Optional<StoreOwner> findStoreOwnerByMail(String mail);

    Optional<StoreOwner> findStoreOwnerByMailAndPassword(String mail, String password);

    Optional<StoreOwner> findStoreOwnerById(Long id);
}
