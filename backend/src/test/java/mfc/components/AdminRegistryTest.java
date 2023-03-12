package mfc.components;

import mfc.POJO.Admin;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.interfaces.explorer.AdminFinder;
import mfc.interfaces.modifier.AdminRegistration;
import mfc.repositories.AdminRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminRegistryTest {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminRegistration adminRegistration;

    @Autowired
    private AdminFinder adminFinder;

    private final String mail = "Admin@pns.fr";
    private final String name = "Mark";
    private final String password = "Landers";


    @BeforeEach
    void setUp() {
        adminRepository.deleteAll();
    }


    @Test
    void unknownAdmin() {
        assertFalse(adminRepository.findByMail(mail).isPresent());
    }

    @Test
    void registerAdmin() throws Exception {
        Admin returned = adminRegistration.registerAdmin(name, mail, password);
        Optional<Admin> admin = adminFinder.findAdminById(returned.getId());
        assertTrue(admin.isPresent());
        Admin mark = admin.get();
        assertEquals(mark, returned);
        assertEquals(mark, adminFinder.findAdminById(returned.getId()).get());
        assertEquals(name, mark.getName());
        assertEquals(mail, mark.getMail());
    }

    @Test
    void cannotRegisterTwice() throws Exception {
        adminRegistration.registerAdmin(name, mail, password);
        Assertions.assertThrows(AlreadyExistingAccountException.class, () -> {
            adminRegistration.registerAdmin(name, mail, password);
        });
    }

    @Test
    void canFindByMail() throws Exception {
        adminRegistration.registerAdmin(name, mail, password);
        Optional<Admin> admin = adminFinder.findAdminByMail(mail);
        assertTrue(admin.isPresent());
        assertEquals(name, admin.get().getName());
    }

    @Test
    void unknownAdminByMail() {
        assertFalse(adminFinder.findAdminByMail(mail).isPresent());
    }

    @Test
    void canFindById() throws Exception {
        Admin admin = adminRegistration.registerAdmin(name, mail, password);
        Optional<Admin> admin2 = adminFinder.findAdminById(admin.getId());
        assertTrue(admin2.isPresent());
    }

    @Test
    void unknownAdminById() {
        Admin admin = new Admin(name, mail, password);
        assertFalse(adminFinder.findAdminById(admin.getId()).isPresent());
    }

    @Test
    void canFindByMailAndPassword() throws Exception {
        adminRegistration.registerAdmin(name, mail, password);
        Optional<Admin> admin = adminFinder.findAdminByMailAndPassword(mail, password);
        assertTrue(admin.isPresent());
        assertEquals(name, admin.get().getName());
    }


    @Test
    void unknownAdminByMailAndPassword() {
        assertFalse(adminFinder.findAdminByMailAndPassword(mail, password).isPresent());
    }

}