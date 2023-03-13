package mfc.cucumber.payoff;

import io.cucumber.java.en.When;
import mfc.components.PayoffHandler;
import mfc.components.registries.CatalogRegistry;
import mfc.exceptions.CustomerNotFoundException;
import mfc.exceptions.NegativePointCostException;
import mfc.exceptions.PayoffNotFoundException;
import mfc.exceptions.VFPExpiredException;
import mfc.interfaces.explorer.CustomerFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClaimPayoff {

    @Autowired
    CustomerFinder customerFinder;
    @Autowired
    PayoffHandler payoffHandler;
    @Autowired
    CatalogRegistry catalogRegistry;

    @When("{string} claims the payoff {string} at the store {string}")
    public void userClaimsThePayoffOfThePurchase(String user, String payoff, String store) throws PayoffNotFoundException, NegativePointCostException, VFPExpiredException, CustomerNotFoundException {
        // Write code here that turns the phrase above into concrete actions
        payoffHandler.claimPayoff(customerFinder.findCustomerByName(user).get(), catalogRegistry.findPayoff(payoff, store).get());
    }

}
