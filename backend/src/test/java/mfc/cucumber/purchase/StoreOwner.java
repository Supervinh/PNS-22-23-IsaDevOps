package mfc.cucumber.purchase;

import io.cucumber.java.en.Given;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.interfaces.modifier.StoreOwnerRegistration;
import mfc.repositories.StoreOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StoreOwner {

    @Autowired
    private StoreOwnerRegistration storeOwnerRegistration;
    @Autowired
    private StoreOwnerRepository storeOwnerRepository;


    @Given("a store owner named {string} with {string} as mail address and {string} as password")
    public void aStoreOwnerNamedWithAsMailAddressAndAsPassword(String name, String mail, String password) throws AlreadyExistingAccountException {
        storeOwnerRepository.deleteAll();
        storeOwnerRegistration.registerStoreOwner(name, mail, password);
    }

}