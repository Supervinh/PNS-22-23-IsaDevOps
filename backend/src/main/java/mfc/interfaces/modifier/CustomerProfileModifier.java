package mfc.interfaces.modifier;

import mfc.POJO.Customer;
import mfc.POJO.Store;
import mfc.interfaces.exceptions.CustomerNotFoundException;
import mfc.interfaces.exceptions.StoreAlreadyRegisteredException;
import mfc.interfaces.exceptions.StoreNotFoundException;

import java.util.Set;

public interface CustomerProfileModifier {
    Customer recordMatriculation(Customer customer, String matriculation) throws CustomerNotFoundException;

    Customer recordCreditCard(Customer customer, String creditCard) throws CustomerNotFoundException;

    Customer recordNewFavoriteStore(Customer customer, Store store) throws StoreNotFoundException, CustomerNotFoundException, StoreAlreadyRegisteredException;

    Customer removeFavoriteStore(Customer customer, Store store) throws StoreNotFoundException, CustomerNotFoundException;

    Customer recordNewFavoriteStores(Customer customer, Set<Store> store) throws CustomerNotFoundException, StoreAlreadyRegisteredException;

    Customer removeAllFavoriteStores(Customer customer, Set<Store> store) throws StoreAlreadyRegisteredException, CustomerNotFoundException;

}
