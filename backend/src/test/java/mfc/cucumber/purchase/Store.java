package mfc.cucumber.purchase;

import io.cucumber.java.en.Given;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.exceptions.AlreadyExistingStoreException;
import mfc.interfaces.modifier.StoreRegistration;
import mfc.repositories.StoreOwnerRepository;
import mfc.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        String[][] scheduleList = new String[7][2];
        for (int i = 0; i <= 6; i++) {
            scheduleList[i][0] = "7h00";
            scheduleList[i][1] = "19h30";
        }
        storeRegistration.register(storeName, scheduleList, storeOwnerRepository.findByMail(ownerMail).get());
    }
}
