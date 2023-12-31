package mfc.components.registries;

import mfc.entities.Admin;
import mfc.exceptions.AccountNotFoundException;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.interfaces.explorer.AdminFinder;
import mfc.interfaces.modifier.AdminRegistration;
import mfc.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@Transactional
public class AdminRegistry implements AdminFinder, AdminRegistration {
    /**
     * AdminRepository
     */
    private final AdminRepository adminRepository;

    @Autowired
    public AdminRegistry(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
        //create a basic Admin by default
        if (adminRepository.findAll().isEmpty())
            adminRepository.save(new Admin("a", "a@a", "a"));
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

    @Override
    public Admin delete(Admin admin) throws AccountNotFoundException {
        if (adminRepository.existsById(admin.getId())) {
            adminRepository.delete(admin);
            return admin;
        }
        throw new AccountNotFoundException();
    }
}