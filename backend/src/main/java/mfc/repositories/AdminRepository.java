package mfc.repositories;

import mfc.POJO.Admin;
import mfc.POJO.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

//@Repository
//public class AdminRepository extends BasicRepositoryImpl<Admin, UUID> {
//
//    public Optional<Admin> findByMail(String mail) {
//        return StreamSupport.stream(findAll().spliterator(), false)
//                .filter(admin -> admin.getMail().equals(mail))
//                .findFirst();
//    }
//
//    public Optional<Admin> findByMailAndPassword(String mail, String password) {
//        return StreamSupport.stream(findAll().spliterator(), false)
//                .filter(admin -> admin.getMail().equals(mail) && admin.getPassword().equals(password))
//                .findFirst();
//    }
//
//}

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findAdminByName(String name);
    Optional<Admin> findAdminByMail(String mail);
    Optional<Admin> findAdminByMailAndPassword(String mail, String password);
    Optional<Admin> findAdminById(Long id);

}
