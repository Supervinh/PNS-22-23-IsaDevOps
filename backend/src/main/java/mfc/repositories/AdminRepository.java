package mfc.repositories;

import mfc.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findAdminByName(String name);
    Optional<Admin> findAdminByMail(String mail);
    Optional<Admin> findAdminByMailAndPassword(String mail, String password);
    Optional<Admin> findAdminById(Long id);

}
