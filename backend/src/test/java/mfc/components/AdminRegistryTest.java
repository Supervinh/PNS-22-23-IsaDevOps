package mfc.components;

import mfc.exceptions.AlreadyExistingAccountException;
import mfc.interfaces.explorer.AdminFinder;
import mfc.interfaces.modifier.AdminRegistration;
import mfc.pojo.Admin;
import mfc.repositories.AdminRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
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
     void unknownadmin() {
        assertFalse(adminRepository.findAdminByMail(mail).isPresent());
    }

    @Test
     void registeradmin() throws Exception {
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
//might need to remove this test
//    @Test
//     void unknownAdminById() {
//        Admin admin = new Admin(name, mail, password);
//        adminRepository.save(admin);
//        assertFalse(adminFinder.findAdminById(admin.getId()).isPresent());
//    }

    @Test
     void canFindByMailAndPassword() throws Exception {
        adminRegistration.registerAdmin(name, mail, password);
        Optional<Admin> admin = adminFinder.findAdminByMailAndPassword(mail, password);
        assertTrue(admin.isPresent());
        assertEquals(name, admin.get().getName());
    }


    @Test
     void unknownAdminByMailAndPassword() {
        assertFalse(adminFinder.findAdminByMailAndPassword(mail,password).isPresent());
    }

}