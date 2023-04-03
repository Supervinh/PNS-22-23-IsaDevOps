package mfc.cucumber.purchase;

import io.cucumber.java.en.Given;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.exceptions.AlreadyExistingStoreException;
import mfc.interfaces.modifier.StoreModifier;
import mfc.repositories.StoreOwnerRepository;
import mfc.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class Store {

    @Autowired
    private StoreModifier storeModifier;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreOwnerRepository storeOwnerRepository;

    @Given("a store named {string}, owned by {string}, with opening hours from {int}:{int} to {int}:{int}")
    public void aStoreNamedOwnedByWithOpeningHoursFromTo(String storeName, String ownerMail, int openingHour, int openingMinute, int closingHour, int closingMinute) throws AlreadyExistingStoreException, AlreadyExistingAccountException {
        storeRepository.deleteAll();
        Map<String, String> scheduleList = new HashMap<>();
        storeModifier.register(storeName, scheduleList, storeOwnerRepository.findStoreOwnerByMail(ownerMail).get());
    }
}
