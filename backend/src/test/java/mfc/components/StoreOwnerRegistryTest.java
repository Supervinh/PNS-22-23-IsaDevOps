package mfc.components;

import mfc.POJO.Admin;
import mfc.POJO.StoreOwner;
import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.exceptions.*;
import mfc.interfaces.explorer.StoreOwnerFinder;
import mfc.interfaces.modifier.AdminRegistration;
import mfc.interfaces.modifier.StoreOwnerRegistration;
import mfc.pojo.StoreOwner;
import mfc.repositories.StoreOwnerRepository;
import mfc.repositories.StoreRepository;
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
class StoreOwnerRegistryTest {

    @Autowired
    private StoreOwnerRepository ownerRepository;

    @Autowired
    private StoreOwnerRegistration ownerRegistration;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreOwnerFinder ownerFinder;

    private final String mail = "Owner@pns.fr";
    private final String name = "Steve";
    private final String password = "Travail";


    @BeforeEach
    void setUp() {
        storeRepository.deleteAll();
        ownerRepository.deleteAll();
    }

    @Test
     void unknownowner() {
        assertFalse(ownerRepository.findStoreOwnerByMail(mail).isPresent());
    }

    @Test
     void registerowner() throws Exception {
        StoreOwner returned = ownerRegistration.registerStoreOwner(name, mail, password);
        Optional<StoreOwner> owner = ownerFinder.findStoreOwnerById(returned.getId());
        assertTrue(owner.isPresent());
        StoreOwner steve = owner.get();
        assertEquals(steve, returned);
        assertEquals(steve, ownerFinder.findStoreOwnerById(returned.getId()).get());
        assertEquals(name, steve.getName());
        assertEquals(mail, steve.getMail());
    }

    @Test
     void cannotRegisterTwice() throws Exception {
        ownerRegistration.registerStoreOwner(name, mail, password);
        Assertions.assertThrows(AlreadyExistingAccountException.class, () -> {
            ownerRegistration.registerStoreOwner(name, mail, password);
        });
    }

    @Test
     void canFindByMail() throws Exception {
        ownerRegistration.registerStoreOwner(name, mail, password);
        Optional<StoreOwner> owner = ownerFinder.findStoreOwnerByMail(mail);
        assertTrue(owner.isPresent());
        assertEquals(name, owner.get().getName());
    }

    @Test
     void unknownStoreOwnerByMail() {
        assertFalse(ownerFinder.findStoreOwnerByMail(mail).isPresent());
    }

    @Test
     void canFindById() throws Exception {
        StoreOwner owner = ownerRegistration.registerStoreOwner(name, mail, password);
        Optional<StoreOwner> owner2 = ownerFinder.findStoreOwnerById(owner.getId());
        assertTrue(owner2.isPresent());
    }

    @Test
     void unknownStoreOwnerById() {
        StoreOwner owner = new StoreOwner(name, mail, password);
        assertFalse(ownerFinder.findStoreOwnerById(owner.getId()).isPresent());
    }

    @Test
     void canFindByMailAndPassword() throws Exception {
        ownerRegistration.registerStoreOwner(name, mail, password);
        Optional<StoreOwner> owner = ownerFinder.findStoreOwnerByMailAndPassword(mail,password);
        assertTrue(owner.isPresent());
        assertEquals(name, owner.get().getName());
    }

    @Test
     void unknownStoreOwnerByMailAndPassword() {
        assertFalse(ownerFinder.findStoreOwnerByMailAndPassword(mail,password).isPresent());
    }

}