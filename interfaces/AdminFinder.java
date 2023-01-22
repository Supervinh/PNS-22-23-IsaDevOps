package interfaces;

import POJO.Admin;
import POJO.StoreOwner;

import java.util.Optional;
import java.util.UUID;

public interface AdminFinder {

    Optional<Admin> findAdminByMail(String mail, String password);
    Optional<Admin> findAdminById(UUID id, String password);

}
