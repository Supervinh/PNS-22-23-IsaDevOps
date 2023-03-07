package mfc.components.registries;

import mfc.POJO.Admin;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.interfaces.explorer.AdminFinder;
import mfc.interfaces.modifier.AdminRegistration;
import mfc.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AdminRegistry implements AdminFinder, AdminRegistration {

    @Autowired
    private final AdminRepository adminRepository;

    public AdminRegistry(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
        //create a basic Admin by default
        Admin admin = new Admin("a","a@a","a");
        adminRepository.save(admin, admin.getId());
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