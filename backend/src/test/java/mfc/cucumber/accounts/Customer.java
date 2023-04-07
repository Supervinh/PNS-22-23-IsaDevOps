package mfc.cucumber.accounts;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.modifier.CustomerRegistration;
import mfc.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Customer {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerRegistration customerRegistration;
    @Autowired
    private CustomerFinder customerFinder;

    @Given("a customer named {string} with {string} as mail address and {string} as password")
    public void aCustomerNamedWithAsMailAddressAndAsPassword(String name, String mail, String password) throws AlreadyExistingAccountException {
        customerRepository.deleteAll();
        customerRegistration.register(name, mail, password, "");
    }

    @Given("{string} has {int} points")
    public void hasPoints(String name, int points) {
        mfc.entities.Customer customer = customerFinder.findCustomerByName(name).get();
        customer.setFidelityPoints(points);
        customerRepository.save(customer);
    }

    @Then("{string} should have {int} points")
    public void shouldHavePoints(String name, int points) {
        mfc.entities.Customer customer = customerFinder.findCustomerByName(name).get();
        assert customer.getFidelityPoints() == points;
    }

    @And("{string} has a fidelity card with a balance of {double} euros")
    public void hasAFidelityCardWithABalanceOfEuros(String name, double balance) {
        mfc.entities.Customer customer = customerFinder.findCustomerByName(name).get();
        customer.setBalance(balance);
        customerRepository.save(customer);
    }
}
