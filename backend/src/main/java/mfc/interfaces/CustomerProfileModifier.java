package mfc.interfaces;

import mfc.POJO.Customer;
import mfc.POJO.Store;
import mfc.interfaces.Exceptions.StoreAlreadyRegisteredException;
import mfc.interfaces.Exceptions.StoreNotFoundException;

import java.util.Set;

public interface CustomerProfileModifier {
    Customer recordMatriculation(Customer customer, String matriculation);

    Customer recordCreditCard(Customer customer, String creditCard);

    Customer recordNewFavoriteStore(Customer customer, Store store) throws StoreNotFoundException;

    Customer removeFavoriteStore(Customer customer, Store store) throws StoreNotFoundException;

    Customer recordNewFavoriteStores(Customer customer, Set<Store> store) throws StoreAlreadyRegisteredException;

    Customer removeAllFavoriteStores(Customer customer, Set<Store> store) throws StoreAlreadyRegisteredException;

}
