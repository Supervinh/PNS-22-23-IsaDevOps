package mfc.interfaces.explorer;

import mfc.POJO.Admin;

import java.util.Optional;
import java.util.UUID;

public interface AdminFinder {
    Optional<Admin> findAdminByMail(String mail, String password);

    Optional<Admin> findAdminById(UUID id, String password);

}

