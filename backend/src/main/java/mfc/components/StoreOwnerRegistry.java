package mfc.components;

import mfc.POJO.StoreOwner;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.interfaces.explorer.StoreOwnerFinder;
import mfc.interfaces.modifier.StoreOwnerRegistration;
import mfc.repositories.StoreOwnerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class StoreOwnerRegistry implements StoreOwnerFinder, StoreOwnerRegistration {

    private final StoreOwnerRepository ownerRepository;

    public StoreOwnerRegistry(StoreOwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Optional<StoreOwner> findStoreOwnerByMail(String mail) {
        return ownerRepository.findStoreOwnerByMail(mail);
    }

    @Override
    public Optional<StoreOwner> findStoreOwnerByMailAndPassword(String mail, String password) {
        return ownerRepository.findStoreOwnerByMailAndPassword(mail, password);
    }

    @Override
    public Optional<StoreOwner> findStoreOwnerById(Long id) {
        return ownerRepository.findById(id);
    }

    @Override
    public Optional<StoreOwner> findStoreOwnerByName(String name) {
        return ownerRepository.findStoreOwnerByName(name);
    }


    @Override
    public StoreOwner registerStoreOwner(String name, String mail, String password) throws AlreadyExistingAccountException {
        Optional<StoreOwner> owner = ownerRepository.findStoreOwnerByMail(mail);
        if (owner.isEmpty()) {
            StoreOwner newOwner = new StoreOwner(name, mail, password);
            ownerRepository.save(newOwner);
            return newOwner;
        }
        throw new AlreadyExistingAccountException();
    }
}