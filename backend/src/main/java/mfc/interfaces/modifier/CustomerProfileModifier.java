package mfc.interfaces.modifier;

import mfc.entities.Customer;
import mfc.entities.Store;
import mfc.exceptions.CustomerNotFoundException;
import mfc.exceptions.StoreAlreadyRegisteredException;
import mfc.exceptions.StoreNotFoundException;

public interface CustomerProfileModifier {
    Customer recordMatriculation(Customer customer, String matriculation) throws CustomerNotFoundException;

    Customer recordCreditCard(Customer customer, String creditCard) throws CustomerNotFoundException;

    Customer recordNewFavoriteStore(Customer customer, Store store) throws StoreNotFoundException, CustomerNotFoundException, StoreAlreadyRegisteredException;

    Customer removeFavoriteStore(Customer customer, Store store) throws StoreNotFoundException, CustomerNotFoundException;

}
