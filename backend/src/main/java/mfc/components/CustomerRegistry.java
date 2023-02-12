package mfc.components;

import mfc.POJO.Customer;
import mfc.POJO.Store;
import mfc.interfaces.Exceptions.*;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.modifier.CustomerBalancesModifier;
import mfc.interfaces.modifier.CustomerProfileModifier;
import mfc.interfaces.modifier.CustomerRegistration;
import mfc.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Component
public class CustomerRegistry implements CustomerRegistration, CustomerFinder, CustomerProfileModifier, CustomerBalancesModifier {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerRegistry(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Override
    public Optional<Customer> findCustomerByMail(String mail) {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .filter(cust -> mail.equals(cust.getMail())).findAny();
    }

    @Override
    public Optional<List<String>> findCustomersMailByStore(Store store) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findCustomerById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Customer editBalance(Customer customer, double balanceChange) throws InsufficientBalanceException {
        return null;
    }

    @Override
    public Customer editFidelityPoints(Customer customer, double balanceChange) throws NegativePointCostException {
        return null;
    }

    @Override
    public Customer recordMatriculation(Customer customer, String matriculation) {
        return null;
    }

    @Override
    public Customer recordCreditCard(Customer customer, String creditCard) {
        return null;
    }

    @Override
    public Customer recordNewFavoriteStore(Customer customer, Store store) throws StoreNotFoundException {
        return null;
    }

    @Override
    public Customer removeFavoriteStore(Customer customer, Store store) throws StoreNotFoundException {
        return null;
    }

    @Override
    public Customer recordNewFavoriteStores(Customer customer, Set<Store> store) throws StoreAlreadyRegisteredException {
        return null;
    }

    @Override
    public Customer removeAllFavoriteStores(Customer customer, Set<Store> store) throws StoreAlreadyRegisteredException {
        return null;
    }

    @Override
    public Customer register(String mail, String password) throws AlreadyExistingAccountException {
        return null;
    }
}
