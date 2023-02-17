package mfc.components;

import mfc.POJO.*;
import mfc.interfaces.exceptions.AlreadyExistingAccountException;
import mfc.interfaces.explorer.AdminFinder;
import mfc.interfaces.explorer.PurchaseFinder;
import mfc.interfaces.modifier.AdminRegistration;
import mfc.interfaces.modifier.PurchaseRecording;
import mfc.interfaces.modifier.StoreOwnerRegistration;
import mfc.repositories.AdminRepository;
import mfc.repositories.PurchaseRepository;
import mfc.repositories.StoreOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Component
public class AdminRegistry implements AdminFinder, AdminRegistration {

    private final AdminRepository adminRepository;

    public AdminRegistry(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Optional<Admin> findAdminByMail(String mail) {
        return adminRepository.findByMail(mail);
    }
    public Optional<Admin> findAdminByMailAndPassword(String mail, String password) {
        return adminRepository.findByMailAndPassword(mail, password);
    }

    @Override
    public Optional<Admin> findAdminById(UUID id) {
        return adminRepository.findById(id);
    }

    @Override
    public Admin registerAdmin(String name, String mail, String password) throws AlreadyExistingAccountException {
        Optional<Admin> admin = adminRepository.findByMail(mail);
        if (admin.isEmpty()) {
            Admin newAdmin = new Admin(name, mail, password);
            adminRepository.save(newAdmin, newAdmin.getId());
            return newAdmin;
        }
        throw new AlreadyExistingAccountException();
    }
}