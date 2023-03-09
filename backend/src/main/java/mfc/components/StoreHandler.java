package mfc.components;

import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.exceptions.AlreadyExistingStoreException;
import mfc.exceptions.CredentialsException;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.modifier.StoreModifier;
import mfc.interfaces.modifier.StoreRegistration;
import mfc.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Component
@Transactional
public class StoreHandler implements StoreFinder, StoreModifier, StoreRegistration {

    private final StoreRepository storeRepository;


    @Autowired
    public StoreHandler(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public Optional<Store> findStoreByName(String name) {
        return StreamSupport.stream(storeRepository.findAll().spliterator(), false)
                .filter(store -> name.equals(store.getName())).findAny();
    }

    @Override
    public Optional<Store> findStoreById(Long id) {
        return storeRepository.findStoreById(id);
    }

    @Override
    public Store register(String name, List<String> schedule, StoreOwner storeOwner) throws AlreadyExistingStoreException {
        Optional<Store> store = findStoreByName(name);
        if (store.isEmpty()) {
            Store newStore = new Store(name, schedule, storeOwner);
            storeRepository.save(newStore);
            return newStore;
        }
        throw new AlreadyExistingStoreException();
    }

    @Override
    public boolean updateOpeningHours(Store store, List<String> schedule, StoreOwner storeOwner) throws CredentialsException {
        Optional<Store> storeToUpdate = findStoreById(store.getId());
        if (storeToUpdate.isPresent()) {
            System.out.println(storeToUpdate.get().getOwner().getName());
            System.out.println(storeOwner.getName());
            if (storeToUpdate.get().getOwner().equals(storeOwner)) {
                storeToUpdate.get().setSchedule(schedule);
                storeRepository.save(storeToUpdate.get());
                return true;
            }
            throw new CredentialsException();
        }
        return false;
    }
}
