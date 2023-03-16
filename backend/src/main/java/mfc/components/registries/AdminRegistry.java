package mfc.components.registries;

import mfc.exceptions.AlreadyExistingAccountException;
import mfc.interfaces.explorer.AdminFinder;
import mfc.interfaces.modifier.AdminRegistration;
import mfc.pojo.Admin;
import mfc.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class AdminRegistry implements AdminFinder, AdminRegistration {

    @Autowired
    private final AdminRepository adminRepository;

    public AdminRegistry(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
        //create a basic Admin by default
        Admin admin = new Admin("a","a@a","a");
        adminRepository.save(admin);
    }

    @Override
    public Optional<Admin> findAdminByMail(String mail) {
        return adminRepository.findAdminByMail(mail);
    }

    public Optional<Admin> findAdminByMailAndPassword(String mail, String password) {
        return adminRepository.findAdminByMailAndPassword(mail, password);
    }

    @Override
    public Optional<Admin> findAdminById(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    public Admin registerAdmin(String name, String mail, String password) throws AlreadyExistingAccountException {
        Optional<Admin> admin = adminRepository.findAdminByMail(mail);
        if (admin.isEmpty()) {
            Admin newAdmin = new Admin(name, mail, password);
            adminRepository.save(newAdmin);
            return newAdmin;
        }
        throw new AlreadyExistingAccountException();
    }
}