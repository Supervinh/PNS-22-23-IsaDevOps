package interfaces;

import POJO.Customer;
import POJO.Store;
import interfaces.Exceptions.InsufficientBalanceException;
import interfaces.Exceptions.NegativePointCostException;
import interfaces.Exceptions.StoreAlreadyRegisteredException;
import interfaces.Exceptions.StoreNotFoundException;

import java.util.Set;

public interface CustomerModifier {
    Customer recordMatriculation(Customer customer, String matriculation);
    Customer recordCreditCard(Customer customer, String creditCard);
    Customer editBalance(Customer customer, double balanceChange) throws InsufficientBalanceException;
    Customer editFidelityPoints(Customer customer, double balanceChange) throws NegativePointCostException;
    Customer recordNewFavoriteStore(Customer customer, Store store) throws StoreNotFoundException;
    Customer removeFavoriteStore(Customer customer, Store store) throws StoreNotFoundException;
    Customer recordNewFavoriteStores(Customer customer, Set<Store> store) throws StoreAlreadyRegisteredException;
    Customer removeAllFavoriteStores(Customer customer, Set<Store> store) throws StoreAlreadyRegisteredException;
}
