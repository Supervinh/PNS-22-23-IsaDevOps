package mfc.interfaces.explorer;

import mfc.entities.Admin;

import java.util.Optional;

public interface AdminFinder {
    Optional<Admin> findAdminByMail(String mail);

    Optional<Admin> findAdminByMailAndPassword(String mail, String password);

    Optional<Admin> findAdminById(Long id);

}

