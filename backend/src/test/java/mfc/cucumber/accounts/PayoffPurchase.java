package mfc.cucumber.accounts;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import mfc.exceptions.AccountNotFoundException;
import mfc.exceptions.PayoffPurchaseNotFoundException;
import mfc.exceptions.StoreNotFoundException;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.explorer.StoreFinder;
import mfc.repositories.PayoffPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static mfc.cucumber.Helper.resetException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class PayoffPurchase {

    @Autowired
    PayoffPurchaseRepository payoffPurchaseRepository;

    @Autowired
    CustomerFinder customerFinder;

    @Autowired
    StoreFinder storeFinder;

    @Before
    public void settingUpContext() {
        resetException();
        payoffPurchaseRepository.deleteAll();
    }

    @And("a payoff is registered for {string} with {string} as payoff and {string} as store")
    public void aPayoffIsRegisteredForWithAsPayoffAndAsStore(String customer, String payoff, String store) {
        assertDoesNotThrow(() -> payoffPurchaseRepository.findByCustomerAndStoreAndName(
                customerFinder.findCustomerByName(customer).orElseThrow(AccountNotFoundException::new),
                storeFinder.findStoreByName(store).orElseThrow(StoreNotFoundException::new),
                payoff).orElseThrow(PayoffPurchaseNotFoundException::new));
    }
}
