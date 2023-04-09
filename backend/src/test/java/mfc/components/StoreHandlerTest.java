package mfc.components;

import mfc.entities.Store;
import mfc.entities.StoreOwner;
import mfc.exceptions.StoreNotFoundException;
import mfc.repositories.StoreOwnerRepository;
import mfc.repositories.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest
@Transactional
class StoreHandlerTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreHandler storeHandler;

    @Autowired
    private StoreOwnerRepository ownerRepository;

    @BeforeEach
    void setUp() {
        storeRepository.deleteAll();
        ownerRepository.deleteAll();
        StoreOwner philippe = new StoreOwner("Philippe", "p@gmail.com", "pwd");
        ownerRepository.save(philippe);
        philippe = ownerRepository.findStoreOwnerByMail(philippe.getMail()).get();
        storeRepository.save(new Store("Leclerc", philippe));
    }

    @Test
    void updateStoreSchedule() throws StoreNotFoundException {
        Optional<Store> carrouf = storeRepository.findStoreByName("Leclerc");
        Map<String, String> tocompare = new HashMap<>(carrouf.orElseThrow(StoreNotFoundException::new).getSchedule());
        Map<String, String> update = new HashMap<>();
        for (int i = 0; i <= 6; i++) {
            update.put("Mo0", "7:20");
            update.put("Mo1", "19:30");
        }
        storeHandler.updateOpeningHours(carrouf.get(), update);
        Store carroufReloaded = storeRepository.findStoreByName("Leclerc").orElseThrow(StoreNotFoundException::new);
        assertNotEquals(tocompare, carroufReloaded.getSchedule());
    }
}
