package mfc.components.registries;

import mfc.entities.StoreOwner;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.exceptions.NoCorrespongingAccountException;
import mfc.interfaces.explorer.StoreOwnerFinder;
import mfc.interfaces.modifier.StoreOwnerRegistration;
import mfc.repositories.StoreOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@Transactional
public class StoreOwnerRegistry implements StoreOwnerFinder, StoreOwnerRegistration {

    private final StoreOwnerRepository ownerRepository;

    @Autowired
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
        Optional<StoreOwner> owner = findStoreOwnerByMail(mail);
        if (owner.isEmpty()) {
            StoreOwner newOwner = new StoreOwner(name, mail, password);
            ownerRepository.save(newOwner);
            return newOwner;
        }
        throw new AlreadyExistingAccountException();
    }

    @Override
    public StoreOwner delete(StoreOwner owner) throws NoCorrespongingAccountException {
        System.out.println("isPresent");
        if (findStoreOwnerById(owner.getId()).isPresent()) {
            System.out.println("after isPresent");
            ownerRepository.delete(owner);
            ownerRepository.flush();
            return owner;
        }
        throw new NoCorrespongingAccountException();
    }
}