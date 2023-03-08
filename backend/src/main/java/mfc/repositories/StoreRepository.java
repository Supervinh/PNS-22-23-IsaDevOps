package mfc.repositories;

import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findStoreByName(String name);
    Optional<Store> findStoreById(Long id);

}

