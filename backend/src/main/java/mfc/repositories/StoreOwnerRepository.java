package mfc.repositories;

import mfc.entities.StoreOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
//public class StoreOwnerRepository extends BasicRepositoryImpl<StoreOwner, UUID> {
//    public Optional<StoreOwner> findByMail(String mail) {
//        return StreamSupport.stream(findAll().spliterator(), false)
//                .filter(owner -> owner.getMail().equals(mail))
//                .findFirst();
//    }
//
//    public Optional<StoreOwner> findByMailAndPassword(String mail, String password) {
//        return StreamSupport.stream(findAll().spliterator(), false)
//                .filter(owner -> owner.getMail().equals(mail) && owner.getPassword().equals(password))
//                .findFirst();
//    }
//
//    public Optional<StoreOwner> findByName(String name) {
//        return StreamSupport.stream(findAll().spliterator(), false)
//                .filter(store -> store.getName().equals(name))
//                .findFirst();
//    }
//
//}

@Repository
public interface StoreOwnerRepository extends JpaRepository<StoreOwner, Long> {

    Optional<StoreOwner> findStoreOwnerByName(String name);
    Optional<StoreOwner> findStoreOwnerByMail(String mail);
    Optional<StoreOwner> findStoreOwnerByMailAndPassword(String mail, String password);
    Optional<StoreOwner> findStoreOwnerById(Long id);

}
