package mfc.components;

import mfc.entities.Store;
import mfc.entities.StoreOwner;
import mfc.repositories.StoreOwnerRepository;
import mfc.repositories.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> setup = new HashMap<>();
//        for(int i = 0; i <= 6; i++){
//            setup.put("8h00");
//            setup.put("20h00");
//        }
        StoreOwner philippe = new StoreOwner("Philippe", "p@gmail.com", "pwd");
        ownerRepository.save(philippe);
        System.out.println(philippe.getId());
        philippe = ownerRepository.findStoreOwnerByMail(philippe.getMail()).get();
        System.out.println(philippe.getId());
        storeRepository.save(new Store("Leclerc", setup, philippe));
    }


//    //    @Test
//     void UpdateStoreSchedule() throws CredentialsException {
//         Map<String, String> update = new HashMap<>();
////        for(int i = 0; i <= 6; i++){
////            update.put("7h30");
////            update.put("19h30");
////        }
//         Optional<Store> carrouf = storeRepository.findStoreByName("Leclerc");
//         Map<String, String> tocompare = carrouf.get().getSchedule();
//         StoreOwner own = carrouf.get().getOwner();
//         storeHandler.updateOpeningHours(carrouf.get(), update, own);
//         Optional<Store> carroufReloaded = storeRepository.findStoreByName("Leclerc");
//         assertNotEquals(tocompare, carroufReloaded.get().getSchedule());
//     }
}
