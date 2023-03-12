package mfc.repositories;

import mfc.pojo.StoreOwner;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Repository
public class StoreOwnerRepository extends BasicRepositoryImpl<StoreOwner, UUID> {
    public Optional<StoreOwner> findByMail(String mail) {
        return StreamSupport.stream(findAll().spliterator(), false)
                .filter(owner -> owner.getMail().equals(mail))
                .findFirst();
    }

    public Optional<StoreOwner> findByMailAndPassword(String mail, String password) {
        return StreamSupport.stream(findAll().spliterator(), false)
                .filter(owner -> owner.getMail().equals(mail) && owner.getPassword().equals(password))
                .findFirst();
    }

    public Optional<StoreOwner> findByName(String name) {
        return StreamSupport.stream(findAll().spliterator(), false)
                .filter(store -> store.getName().equals(name))
                .findFirst();
    }

}
