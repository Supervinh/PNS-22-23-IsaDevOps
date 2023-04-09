package mfc.repositories;

import mfc.entities.Store;
import mfc.entities.StoreOwner;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByOwner(StoreOwner owner);

    void deleteByOwner(StoreOwner owner);

    Optional<Store> findStoreByName(String name);

    @Override
    @NotNull
    List<Store> findAll();

    @Modifying
    @Query(value = "DELETE FROM customer_favorite_stores s WHERE s.favorite_stores_id = ?1", nativeQuery = true)
    void deleteFromFavoritesStores(Long id);
}

