package mfc.components;

import mfc.entities.Store;
import mfc.entities.StoreOwner;
import mfc.exceptions.AlreadyExistingStoreException;
import mfc.exceptions.NoStoreFoundException;
import mfc.exceptions.StoreNotFoundException;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.modifier.StoreModifier;
import mfc.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Component
@Transactional
public class StoreHandler implements StoreFinder, StoreModifier {

    private final StoreRepository storeRepository;


    @Autowired
    public StoreHandler(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public Optional<Store> findStoreByName(String name) {
        return storeRepository.findAll().stream().filter(store -> name.equals(store.getName())).findAny();
    }

    @Override
    public Optional<Store> findStoreById(Long id) {
        return storeRepository.findStoreById(id);
    }

    @Override
    public Store register(String name, Map<String, String> schedule, StoreOwner storeOwner) throws AlreadyExistingStoreException {
        Optional<Store> store = findStoreByName(name);
        if (store.isEmpty()) {
            Store newStore = new Store(name, schedule, storeOwner);
            storeRepository.save(newStore);
            return newStore;
        }
        throw new AlreadyExistingStoreException();
    }

    @Override
    public Store delete(Store store) throws NoStoreFoundException {
        if (storeRepository.existsById(store.getId())) {
            storeRepository.delete(store);
            return store;
        }
        throw new NoStoreFoundException();
    }

    @Override
    public Store updateOpeningHours(Store store, Map<String, String> schedule) throws StoreNotFoundException {
        Store storeToUpdate = findStoreByName(store.getName()).orElseThrow(StoreNotFoundException::new);
        storeToUpdate.getSchedule().putAll(schedule);
        storeToUpdate.setLastUpdate(LocalDateTime.now());
        return storeRepository.save(storeToUpdate);
    }
}
