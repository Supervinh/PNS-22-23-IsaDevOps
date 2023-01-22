package interfaces;

import POJO.Admin;
import POJO.StoreOwner;
import interfaces.Exceptions.AlreadyExistingAccountException;

public interface StoreOwnerRegistration {
    StoreOwner register(String mail, String password, Admin authorization) throws AlreadyExistingAccountException;

}
