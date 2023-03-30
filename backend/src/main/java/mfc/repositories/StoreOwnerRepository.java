package mfc.repositories;

import mfc.entities.StoreOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreOwnerRepository extends JpaRepository<StoreOwner, Long> {

    Optional<StoreOwner> findStoreOwnerByName(String name);
    Optional<StoreOwner> findStoreOwnerByMail(String mail);
    Optional<StoreOwner> findStoreOwnerByMailAndPassword(String mail, String password);
    Optional<StoreOwner> findStoreOwnerById(Long id);

}
