package mfc.cucumber.purchase;

import io.cucumber.java.en.Given;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.exceptions.AlreadyExistingStoreException;
import mfc.interfaces.modifier.StoreRegistration;
import mfc.repositories.StoreOwnerRepository;
import mfc.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class Store {

    @Autowired
    private StoreRegistration storeRegistration;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreOwnerRepository storeOwnerRepository;

    @Given("a store named {string}, owned by {string}, with opening hours from {int}:{int} to {int}:{int}")
    public void aStoreNamedOwnedByWithOpeningHoursFromTo(String storeName, String ownerMail, int openingHour, int openingMinute, int closingHour, int closingMinute) throws AlreadyExistingStoreException, AlreadyExistingAccountException {
        storeRepository.deleteAll();
        List<String> scheduleList = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            scheduleList.add("7h00");
            scheduleList.add("19h30");
        }
        storeRegistration.register(storeName, scheduleList, storeOwnerRepository.findStoreOwnerByMail(ownerMail).get());
    }
}
