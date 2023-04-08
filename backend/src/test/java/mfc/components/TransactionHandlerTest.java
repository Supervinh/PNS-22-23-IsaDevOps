package mfc.components;

import mfc.entities.Customer;
import mfc.entities.Purchase;
import mfc.entities.Store;
import mfc.exceptions.CustomerNotFoundException;
import mfc.exceptions.NegativePointCostException;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.explorer.PurchaseFinder;
import mfc.interfaces.modifier.CustomerBalancesModifier;
import mfc.interfaces.modifier.CustomerProfileModifier;
import mfc.interfaces.modifier.CustomerRegistration;
import mfc.interfaces.modifier.PurchaseRecording;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class TransactionHandlerTest {

    Customer customer;
    Store s;
    @Autowired
    TransactionHandler transactionalHandler;
    @MockBean
    private PurchaseFinder purchaseFinder;
    @MockBean
    private PurchaseRecording purchaseRecording;
    @MockBean
    private CustomerFinder customerFinder;
    @MockBean
    private CustomerBalancesModifier customerBalancesModifier;
    @MockBean
    private CustomerRegistration customerRegistration;
    @MockBean
    private CustomerProfileModifier customerProfileModifier;

    @BeforeEach
    void setUp() throws CustomerNotFoundException {
        s = new Store("StoreA", null, null);
        customer = new Customer("Mark", "a@a.fr", "password", "0123456789");
        customer.setFidelityPoints(50);
        Purchase purchase1 = new Purchase(200, customer, s);
        purchase1.setDate(LocalDate.now().minusDays(6));
        Purchase purchase2 = new Purchase(200, customer, s);
        purchase2.setDate(LocalDate.now().minusDays(4));
        Purchase purchase3 = new Purchase(200, customer, s);
        purchase3.setDate(LocalDate.now().minusDays(5));
        Purchase purchase4 = new Purchase(200, customer, s);
        when(purchaseFinder.lookUpPurchasesByCustomer(any())).thenReturn(Set.of(purchase1, purchase2, purchase3, purchase4));
    }

    @Test
    void updateVfp() throws NegativePointCostException, CustomerNotFoundException {
        transactionalHandler.purchase(customer, 40, s);
        Mockito.verify(customerBalancesModifier, Mockito.times(1)).editVFP(customer, LocalDate.now().plusDays(7));
    }
}