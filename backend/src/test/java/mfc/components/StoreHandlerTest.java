package mfc.components;

import mfc.POJO.Schedule;
import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.exceptions.CredentialsException;
import mfc.repositories.StoreOwnerRepository;
import mfc.repositories.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.constraints.AssertTrue;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class StoreHandlerTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreHandler storeHandler;

    @BeforeEach
    void setUp() {
        storeRepository.deleteAll();
        List<Schedule> setupList = new ArrayList<>();
        for(int i = 0; i <= 6; i++){
            Schedule s = new Schedule(LocalTime.of(8,0), LocalTime.of(20,0));
            setupList.add(s);
        }
        storeRepository.save(new Store("Leclerc",setupList,new StoreOwner("Philippe", "p@gmail.com", "pwd")), UUID.randomUUID());
    }


    @Test
    public void UpdateStoreSchedule() throws CredentialsException {
        List<Schedule> update = new ArrayList<>();
        for(int i = 0; i <= 6; i++){
            Schedule s = new Schedule(LocalTime.of(7,0), LocalTime.of(21,0));
                update.add(s);
        }
        Optional<Store> carrouf = storeRepository.findByName("Leclerc");
        List<Schedule> tocompare = carrouf.get().getSchedule();
        StoreOwner own = carrouf.get().getOwner();
        storeHandler.updateOpeningHours(carrouf.get(), update, own);

        Optional<Store> carroufReloaded = storeRepository.findByName("Leclerc");

        assertNotEquals(tocompare,carroufReloaded.get().getSchedule());

    }
}
