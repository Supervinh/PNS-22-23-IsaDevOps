package mfc.interfaces.explorer;

import mfc.pojo.Admin;

import java.util.Optional;
import java.util.UUID;

public interface AdminFinder {
    Optional<Admin> findAdminByMail(String mail);

    Optional<Admin> findAdminByMailAndPassword(String mail, String password);

    Optional<Admin> findAdminById(Long id);

}

