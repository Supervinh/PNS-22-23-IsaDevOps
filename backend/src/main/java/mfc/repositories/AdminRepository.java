package mfc.repositories;

import mfc.POJO.Admin;
import mfc.POJO.Customer;
import org.springframework.stereotype.Repository;
import repositories.BasicRepositoryImpl;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Repository
public class AdminRepository extends BasicRepositoryImpl<Admin, UUID> {
    public Optional<Admin> findByMail(String mail, String password) {
        return StreamSupport.stream(findAll().spliterator(), false)
                .filter(admin -> admin.getMail().equals(mail) && admin.getPassword().equals(password))
                .findFirst();
    }
    public Optional<Admin> findById(UUID id, String password) {
        return StreamSupport.stream(findAll().spliterator(), false)
                .filter(admin -> admin.getId().equals(id) && admin.getPassword().equals(password))
                .findFirst();
    }
}
