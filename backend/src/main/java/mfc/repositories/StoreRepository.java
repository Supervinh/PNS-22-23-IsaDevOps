package mfc.repositories;

import mfc.POJO.Customer;
import mfc.POJO.Store;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Repository
public class StoreRepository extends BasicRepositoryImpl<Store, UUID> {
    public Optional<Store> findByName(String name) {
        return StreamSupport.stream(findAll().spliterator(), false)
                .filter(store -> store.getName().equals(name))
                .findFirst();
    }
}
