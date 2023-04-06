package mfc.components.registries;

import mfc.entities.*;
import mfc.exceptions.InsufficientBalanceException;
import mfc.exceptions.NoPreviousPurchaseException;
import mfc.exceptions.VFPExpiredException;
import mfc.interfaces.explorer.PurchaseFinder;
import mfc.interfaces.modifier.PurchaseRecording;
import mfc.repositories.PayoffRepository;
import mfc.repositories.StoreOwnerRepository;
import mfc.repositories.StoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class CatalogRegistryTest {

    Payoff firstNoVfp;
    Payoff firstVfp;
    Payoff second;
    Payoff firstExpensive;
    @Autowired
    private PayoffRepository payoffRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private StoreOwnerRepository storeOwnerRepository;
    @Autowired
    private CatalogRegistry catalogRegistry;
    @MockBean
    private PurchaseFinder purchaseFinder;
    @MockBean
    private PurchaseRecording purchaseRecording;
    private Customer john;
    private Customer robert;
    private Customer bob;

    @BeforeEach
    void setUp() {
        john = new Customer("john", "john@mail.com", "01234", "");
        robert = new Customer("robert", "robert@mail.com", "012354", "");
        bob = new Customer("bob", "bob@mail.com", "012354", "");
        robert.setFidelityPoints(500);
        john.setFidelityPoints(500);
        bob.setVfp(LocalDate.now().minusDays(1));
        bob.setFidelityPoints(350);
        john.setVfp(LocalDate.now().minusDays(1));
        StoreOwner owner = new StoreOwner("owner", "owner@mail.com", "owner");
        Store firstStore = new Store("a", owner);
        firstStore.setName("first");
        Store secondStore = new Store("b", owner);
        secondStore.setName("second");
        firstNoVfp = new Payoff("firstNoVfp", 850, 250, firstStore, false);
        firstVfp = new Payoff("firstVfp", 850, 250, firstStore, true);
        firstExpensive = new Payoff("firstVfp", 850, 1000, firstStore, false);
        second = new Payoff("second", 850, 250, secondStore, false);

        storeRepository.save(firstStore);
        storeRepository.save(secondStore);
        storeOwnerRepository.save(owner);

        payoffRepository.save(firstNoVfp);
        payoffRepository.save(firstVfp);
        payoffRepository.save(second);
        payoffRepository.save(firstExpensive);
        when(purchaseFinder.lookUpPurchasesByCustomer(john)).thenReturn(Set.of(new Purchase(400, john, firstStore)));
        when(purchaseFinder.lookUpPurchasesByCustomer(robert)).thenReturn(new HashSet<>());
        when(purchaseFinder.lookUpPurchasesByCustomer(bob)).thenReturn(Set.of(new Purchase(600, bob, firstStore), new Purchase(400, bob, secondStore)));
    }

    @Test
    void isAvailablePayoff() {
        assertDoesNotThrow(() -> catalogRegistry.isAvailablePayoff(john, firstNoVfp));
    }

    @Test
    void isAvailablePayoffNoPurchase() {
        assertThrows(NoPreviousPurchaseException.class, () -> catalogRegistry.isAvailablePayoff(robert, firstNoVfp));
    }

    @Test
    void isAvailablePayoffLowBalance() {
        assertThrows(InsufficientBalanceException.class, () -> catalogRegistry.isAvailablePayoff(john, firstExpensive));
    }

    @Test
    void isAvailablePayoffNoVfp() {
        assertThrows(VFPExpiredException.class, () -> catalogRegistry.isAvailablePayoff(john, firstVfp));
    }

    @Test
    void showAvailablePayoffs() {
        Assertions.assertEquals(2, catalogRegistry.showAvailablePayoffs(bob).size());
    }

}