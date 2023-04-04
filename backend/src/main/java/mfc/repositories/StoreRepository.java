package mfc.repositories;

import mfc.entities.Store;
import mfc.entities.StoreOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Set<Store> findByOwner(StoreOwner owner);

    Optional<Store> findStoreByName(String name);

    Optional<Store> findStoreById(Long id);

}

