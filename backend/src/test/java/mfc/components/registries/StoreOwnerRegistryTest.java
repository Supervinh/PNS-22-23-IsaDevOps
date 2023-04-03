package mfc.components.registries;

import mfc.entities.StoreOwner;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.interfaces.explorer.StoreOwnerFinder;
import mfc.interfaces.modifier.StoreOwnerRegistration;
import mfc.repositories.StoreOwnerRepository;
import mfc.repositories.StoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StoreOwnerRegistryTest {

    private final String mail = "Owner@pns.fr";
    private final String name = "Steve";
    private final String password = "Travail";
    @Autowired
    private StoreOwnerRepository ownerRepository;
    @Autowired
    private StoreOwnerRegistration ownerRegistration;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private StoreOwnerFinder ownerFinder;

    @BeforeEach
    void setUp() {
        storeRepository.deleteAll();
        ownerRepository.deleteAll();
    }

    @Test
    void unknownOwner() {
        assertFalse(ownerRepository.findStoreOwnerByMail(mail).isPresent());
    }

    @Test
    void registerOwner() throws Exception {
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
    void canFindByMailAndPassword() throws Exception {
        ownerRegistration.registerStoreOwner(name, mail, password);
        Optional<StoreOwner> owner = ownerFinder.findStoreOwnerByMailAndPassword(mail, password);
        assertTrue(owner.isPresent());
        assertEquals(name, owner.get().getName());
    }

    @Test
    void unknownStoreOwnerByMailAndPassword() {
        assertFalse(ownerFinder.findStoreOwnerByMailAndPassword(mail, password).isPresent());
    }

}