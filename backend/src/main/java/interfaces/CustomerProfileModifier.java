package interfaces;

import POJO.Customer;
import POJO.Store;
import interfaces.Exceptions.StoreAlreadyRegisteredException;
import interfaces.Exceptions.StoreNotFoundException;

import java.util.Set;

public interface CustomerProfileModifier {
    Customer recordMatriculation(Customer customer, String matriculation);

    Customer recordCreditCard(Customer customer, String creditCard);

    Customer recordNewFavoriteStore(Customer customer, Store store) throws StoreNotFoundException;

    Customer removeFavoriteStore(Customer customer, Store store) throws StoreNotFoundException;

    Customer recordNewFavoriteStores(Customer customer, Set<Store> store) throws StoreAlreadyRegisteredException;

    Customer removeAllFavoriteStores(Customer customer, Set<Store> store) throws StoreAlreadyRegisteredException;

}
