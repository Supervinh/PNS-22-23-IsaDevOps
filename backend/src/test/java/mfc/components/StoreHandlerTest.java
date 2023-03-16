package mfc.components;

import mfc.exceptions.CredentialsException;
import mfc.repositories.StoreOwnerRepository;
import mfc.repositories.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
        List<String> setupList = new ArrayList<>();
        for(int i = 0; i <= 6; i++){
            setupList.add("8h00");
            setupList.add("20h00");
        }
        StoreOwner philippe = new StoreOwner("Philippe", "p@gmail.com", "pwd");
        ownerRepository.save(philippe);
        System.out.println(philippe.getId());
        philippe = ownerRepository.findStoreOwnerByMail(philippe.getMail()).get();
        System.out.println(philippe.getId());
        storeRepository.save(new Store("Leclerc",setupList,philippe));
    }


    @Test
     void UpdateStoreSchedule() throws CredentialsException {
        List<String> update = new ArrayList<>();
        for(int i = 0; i <= 6; i++){
            update.add("7h30");
            update.add("19h30");
        }
        Optional<Store> carrouf = storeRepository.findStoreByName("Leclerc");
        List<String> tocompare = carrouf.get().getSchedule();
        StoreOwner own = carrouf.get().getOwner();
        storeHandler.updateOpeningHours(carrouf.get(), update, own);

        Optional<Store> carroufReloaded = storeRepository.findStoreByName("Leclerc");

        assertNotEquals(tocompare,carroufReloaded.get().getSchedule());

    }
}
