package mfc.cucumber.accounts;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.exceptions.AlreadyExistingStoreException;
import mfc.exceptions.StoreOwnerNotFoundException;
import mfc.interfaces.modifier.StoreModifier;
import mfc.interfaces.modifier.StoreOwnerRegistration;
import mfc.repositories.StoreOwnerRepository;
import mfc.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
public class StoreOwner {

    @Autowired
    private StoreOwnerRegistration storeOwnerRegistration;
    @Autowired
    private StoreOwnerRepository storeOwnerRepository;

    @Autowired
    StoreModifier storeModifier;

    @Autowired
    private StoreRepository storeRepository;


    @Given("a store owner named {string} with {string} as mail address and {string} as password")
    public void aStoreOwnerNamedWithAsMailAddressAndAsPassword(String name, String mail, String password) throws AlreadyExistingAccountException {
        //TODO comprendre pourquoi le storeOwnerRepository.deleteAll() ne delete pas le storeRepository
        storeRepository.deleteAll();
        storeOwnerRepository.deleteAll();
        storeOwnerRegistration.registerStoreOwner(name, mail, password);
    }

    @Given("an owner named {string} with {string} as mail address and {string} as password")
    public void anOwnerNamedWithAsMailAddressAndAsPassword(String name, String mail, String password) throws AlreadyExistingAccountException {
        storeOwnerRegistration.registerStoreOwner(name, mail, password);
    }


    @And("{string} own the store {string}")
    public void ownTheStore(String owner, String string) throws StoreOwnerNotFoundException, AlreadyExistingStoreException {
        storeModifier.register(string, new HashMap<>(), storeOwnerRepository.findStoreOwnerByName(owner).orElseThrow(StoreOwnerNotFoundException::new));
    }
}
