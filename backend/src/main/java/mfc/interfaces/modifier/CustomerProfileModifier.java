package mfc.interfaces.modifier;

import mfc.entities.Customer;
import mfc.entities.Store;
import mfc.exceptions.AccountNotFoundException;
import mfc.exceptions.AlreadyRegisteredStoreException;
import mfc.exceptions.StoreNotFoundException;

public interface CustomerProfileModifier {
    Customer recordMatriculation(Customer customer, String matriculation) throws AccountNotFoundException;

    Customer recordCreditCard(Customer customer, String creditCard) throws AccountNotFoundException;

    Customer recordNewFavoriteStore(Customer customer, Store store) throws StoreNotFoundException, AccountNotFoundException, AlreadyRegisteredStoreException;

    Customer removeFavoriteStore(Customer customer, Store store) throws StoreNotFoundException, AccountNotFoundException;

}
