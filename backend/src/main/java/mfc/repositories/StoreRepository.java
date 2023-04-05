package mfc.repositories;

import mfc.entities.Store;
import mfc.entities.StoreOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Set<Store> findByOwner(StoreOwner owner);

    Optional<Store> findStoreByName(String name);

    Optional<Store> findStoreById(Long id);

    @Modifying
    @Query(value = "DELETE FROM CUSTOMER_FAVORITE_STORES WHERE favorite_stores_id = ?1", nativeQuery = true)
    void deleteFavoriteStoresFromUsers(Long id);

    default void deleteStore(Store store) {
        deleteFavoriteStoresFromUsers(store.getId());
        deleteById(store.getId());
    }

}

