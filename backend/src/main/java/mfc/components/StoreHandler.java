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

import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Component
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
    public Optional<Store> findStoreById(UUID id) {
        return StreamSupport.stream(storeRepository.findAll().spliterator(), false)
                .filter(store -> id.equals(store.getId())).findAny();
    }

//    @Override
//    public Optional<Map<LocalTime, LocalTime>> findStoreOpeningHours(Store store) {
//        return StreamSupport.stream(storeRepository.findAll().spliterator(), false)
//                .filter(store::equals).findAny().map(Store::getOpeningHours);
//    }

    @Override
    public Store register(/*Map<LocalTime, LocalTime> openingHours, */StoreOwner storeOwner, String name) throws AlreadyExistingStoreException {
        Optional<Store> store = findStoreByName(name);
        if (store.isEmpty()) {
            Store newStore = new Store(name/*, openingHours*/, storeOwner);
            storeRepository.save(newStore, newStore.getId());
            return newStore;
        }
        throw new AlreadyExistingStoreException();
    }

    @Override
    public boolean updateOpeningHours(Store store, Map<LocalTime, LocalTime> openingHours, StoreOwner storeOwner) throws CredentialsException {
        Optional<Store> storeToUpdate = findStoreById(store.getId());
        if (storeToUpdate.isPresent()) {
            if (storeToUpdate.get().getOwner().equals(storeOwner)) {
//                storeToUpdate.get().setOpeningHours(openingHours);
                storeRepository.save(storeToUpdate.get(), storeToUpdate.get().getId());
                return true;
            }
            throw new CredentialsException();
        }
        return false;
    }
}
