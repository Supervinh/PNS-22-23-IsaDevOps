package mfc.components;

import mfc.exceptions.CredentialsException;
import mfc.pojo.Store;
import mfc.pojo.StoreOwner;
import mfc.repositories.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class StoreHandlerTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreHandler storeHandler;

    @BeforeEach
    void setUp() {
        storeRepository.deleteAll();
        String[][] setupList = new String[7][2];
        for(int i = 0; i <= 6; i++){
            setupList[i][0] = "8h00";
            setupList[i][1] = "20h00";
        }
        storeRepository.save(new Store("Leclerc",setupList,new StoreOwner("Philippe", "p@gmail.com", "pwd")), UUID.randomUUID());
    }


    @Test
    void UpdateStoreSchedule() throws CredentialsException {
        String[][] update = new String[7][2];
        for (int i = 0; i <= 6; i++) {
            update[i][0] = "7h00";
            update[i][1] = "19h30";
        }
        Optional<Store> carrouf = storeRepository.findByName("Leclerc");
        String[][] tocompare = carrouf.get().getSchedule();
        StoreOwner own = carrouf.get().getOwner();
        storeHandler.updateOpeningHours(carrouf.get(), update, own);

        Optional<Store> carroufReloaded = storeRepository.findByName("Leclerc");

        assertNotEquals(tocompare,carroufReloaded.get().getSchedule());

    }
}
