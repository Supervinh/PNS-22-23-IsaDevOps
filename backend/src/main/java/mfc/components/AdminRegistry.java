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

    private final StoreOwnerRepository ownerRepository;

    public AdminRegistry(AdminRepository adminRepository, StoreOwnerRepository ownerRepository) {
        this.adminRepository = adminRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Optional<Admin> findAdminByMail(String mail, String password) {
        return adminRepository.findByMail(mail, password);
    }

    @Override
    public Optional<Admin> findAdminById(UUID id, String password) {
        return adminRepository.findById(id, password);
    }

    @Override
    public Admin registerAdmin(String name, String mail, String password, Admin authorization) throws AlreadyExistingAccountException {
        Optional<Admin> admin = adminRepository.findByMail(mail, password);
        if (admin.isEmpty()) {
            Admin newAdmin = new Admin(name, mail, password);
            adminRepository.save(newAdmin, newAdmin.getId());
            return newAdmin;
        }
        throw new AlreadyExistingAccountException();
    }
//  FOR STORE OWNER REGISTRY
//    @Override
//    public StoreOwner registerStoreOwner(String name, String mail, String password, Admin authorization) throws AlreadyExistingAccountException {
//        Optional<StoreOwner> owner = ownerRepository.findByMail(mail, password);
//        if (owner.isEmpty()) {
//            StoreOwner newOwner = new StoreOwner(name, mail, password);
//            ownerRepository.save(newOwner, newOwner.getId());
//            return newOwner;
//        }
//        throw new AlreadyExistingAccountException();
//    }
}