package mfc.cucumber.owners;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import mfc.exceptions.AlreadyExistingStoreException;
import mfc.interfaces.modifier.StoreModifier;
import mfc.repositories.StoreOwnerRepository;
import mfc.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static mfc.cucumber.Helper.resetException;

@SpringBootTest
public class Store {

    @Autowired
    private StoreModifier storeModifier;

    @Autowired
    private StoreOwnerRepository storeOwnerRepository;
    @Autowired
    private StoreRepository storeRepository;

    @Before
    public void settingUpContext() {
        resetException();
        storeRepository.deleteAll();
        System.out.println("Store deleted");
    }

    @Given("a store named {string}, owned by {string}")
    public void aStoreNamedOwnedByWithOpeningHoursFromTo(String storeName, String ownerMail) throws AlreadyExistingStoreException {
        Map<String, String> scheduleList = new HashMap<>();
        storeModifier.register(storeName, scheduleList, storeOwnerRepository.findStoreOwnerByMail(ownerMail).get());
    }
}
