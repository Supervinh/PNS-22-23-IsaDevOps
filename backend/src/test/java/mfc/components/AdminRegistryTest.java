package mfc.components;

import mfc.POJO.Admin;
import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.exceptions.*;
import mfc.interfaces.explorer.AdminFinder;
import mfc.interfaces.modifier.AdminRegistration;
import mfc.repositories.AdminRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
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
    public void unknownadmin() {
        assertFalse(adminRepository.findAdminByMail(mail).isPresent());
    }

    @Test
    public void registeradmin() throws Exception {
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
    public void cannotRegisterTwice() throws Exception {
        adminRegistration.registerAdmin(name, mail, password);
        Assertions.assertThrows(AlreadyExistingAccountException.class, () -> {
            adminRegistration.registerAdmin(name, mail, password);
        });
    }

    @Test
    public void canFindByMail() throws Exception {
        adminRegistration.registerAdmin(name, mail, password);
        Optional<Admin> admin = adminFinder.findAdminByMail(mail);
        assertTrue(admin.isPresent());
        assertEquals(name, admin.get().getName());
    }

    @Test
    public void unknownAdminByMail() {
        assertFalse(adminFinder.findAdminByMail(mail).isPresent());
    }

    @Test
    public void canFindById() throws Exception {
        Admin admin = adminRegistration.registerAdmin(name, mail, password);
        Optional<Admin> admin2 = adminFinder.findAdminById(admin.getId());
        assertTrue(admin2.isPresent());
    }
//might need to remove this test
//    @Test
//    public void unknownAdminById() {
//        Admin admin = new Admin(name, mail, password);
//        adminRepository.save(admin);
//        assertFalse(adminFinder.findAdminById(admin.getId()).isPresent());
//    }

    @Test
    public void canFindByMailAndPassword() throws Exception {
        adminRegistration.registerAdmin(name, mail, password);
        Optional<Admin> admin = adminFinder.findAdminByMailAndPassword(mail,password);
        assertTrue(admin.isPresent());
        assertEquals(name, admin.get().getName());
    }


    @Test
    public void unknownAdminByMailAndPassword() {
        assertFalse(adminFinder.findAdminByMailAndPassword(mail,password).isPresent());
    }

}