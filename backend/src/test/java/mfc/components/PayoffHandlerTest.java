package mfc.components;

import mfc.components.registries.CatalogRegistry;
import mfc.components.registries.CustomerRegistry;
import mfc.entities.PayoffPurchase;
import mfc.entities.Store;
import mfc.exceptions.*;
import mfc.entities.Payoff;
import mfc.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class PayoffHandlerTest {

    @MockBean
    CatalogRegistry catalogRegistry;
    Set<Payoff> payoffs;
    Customer customer;
    Customer noPayOffCustomer;
    Payoff low;
    Payoff medium;
    Payoff expensive;
    @MockBean
    private CustomerRegistry customerRegistry;
    @Autowired
    private PayoffHandler payoffHandler;

    @BeforeEach
    void setUp() throws NegativePointCostException, CustomerNotFoundException {
        low = new Payoff("low", 10, 10, new Store("StoreA", null, null));
        medium = new Payoff("medium", 25, 25, new Store("StoreA", null, null));
        expensive = new Payoff("expensive", 50, 50, new Store("StoreC", null, null));
        payoffs = new HashSet<>(Set.of(low, medium));
        customer = new Customer("Mark", "a@a.fr", "password", "0123456789");
        noPayOffCustomer = new Customer("Mark", "a@a.fr", "password", "0123456789");
        customer.setFidelityPoints(50);
        when(catalogRegistry.availablePayoffs(customer)).thenReturn(payoffs);
        when(catalogRegistry.availablePayoffs(noPayOffCustomer)).thenReturn(new HashSet<>());
        when(customerRegistry.editVFP(eq(customer), any())).thenReturn(customer);
        when(customerRegistry.editFidelityPoints(eq(customer), anyInt())).thenReturn(customer);
    }

    @Test
    void claimPayoff() throws VFPExpiredException, NegativePointCostException, CustomerNotFoundException, NoMatriculationException, ParkingException {
        assertEquals(new PayoffPurchase("low", 10, 10, low.getStore(), customer), payoffHandler.claimPayoff(customer, low));
    }

    @Test
    void claimPayoffEditCustomer() throws VFPExpiredException, NegativePointCostException, CustomerNotFoundException, NoMatriculationException, ParkingException {
        payoffHandler.claimPayoff(customer, low);
        Mockito.verify(customerRegistry, Mockito.times(1)).editFidelityPoints(customer, -10);
        Mockito.verify(customerRegistry, Mockito.times(1)).editVFP(customer, LocalDate.now().plusDays(2));
    }

    @Test
    void claimPayoffInvalid() {
        assertThrows(VFPExpiredException.class, () -> payoffHandler.claimPayoff(customer, expensive));
    }

    @Test
    void claimPayoffNoPayoff() {
        assertThrows(VFPExpiredException.class, () -> payoffHandler.claimPayoff(noPayOffCustomer, low));
    }
}
