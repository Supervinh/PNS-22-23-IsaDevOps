package mfc.cucumber.accounts;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import mfc.exceptions.AccountNotFoundException;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.exceptions.AlreadyExistingStoreException;
import mfc.interfaces.modifier.StoreModifier;
import mfc.interfaces.modifier.StoreOwnerRegistration;
import mfc.repositories.StoreOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static mfc.cucumber.Helper.resetException;

@SpringBootTest
public class StoreOwner {

    @Autowired
    StoreModifier storeModifier;
    @Autowired
    private StoreOwnerRegistration storeOwnerRegistration;
    @Autowired
    private StoreOwnerRepository storeOwnerRepository;

    @Before
    public void settingUpContext() {
        resetException();
        storeOwnerRepository.deleteAll();
    }

    @Given("a store owner named {string} with {string} as mail address and {string} as password")
    public void aStoreOwnerNamedWithAsMailAddressAndAsPassword(String name, String mail, String password) throws AlreadyExistingAccountException {
        storeOwnerRegistration.registerStoreOwner(name, mail, password);
    }

    @Given("an owner named {string} with {string} as mail address and {string} as password")
    public void anOwnerNamedWithAsMailAddressAndAsPassword(String name, String mail, String password) throws AlreadyExistingAccountException {
        storeOwnerRegistration.registerStoreOwner(name, mail, password);
    }


    @And("{string} own the store {string}")
    public void ownTheStore(String owner, String string) throws AccountNotFoundException, AlreadyExistingStoreException {
        storeModifier.register(string, new HashMap<>(), storeOwnerRepository.findStoreOwnerByName(owner).orElseThrow(AccountNotFoundException::new));
    }
}
