package mfc.components.registries;

import mfc.POJO.Customer;
import mfc.POJO.Store;
import mfc.exceptions.*;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.modifier.CustomerBalancesModifier;
import mfc.interfaces.modifier.CustomerProfileModifier;
import mfc.interfaces.modifier.CustomerRegistration;
import mfc.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

@Component
@Transactional
public class CustomerRegistry implements CustomerRegistration, CustomerFinder, CustomerProfileModifier, CustomerBalancesModifier {

    private final CustomerRepository customerRepository;

    @Autowired // annotation is optional since Spring 4.3 if component has only one constructor
    public CustomerRegistry(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer register(String name, String mail, String password) throws AlreadyExistingAccountException {
        if (findCustomerByName(name).isPresent()) throw new AlreadyExistingAccountException();
        Customer newcustomer = new Customer(name, mail, password);
        customerRepository.save(newcustomer);
        return newcustomer;
    }

    @Override
    public Customer register(String name, String mail, String password, String creditCard) throws AlreadyExistingAccountException {
        if (findCustomerByName(name).isPresent()) throw new AlreadyExistingAccountException();
        Customer newcustomer = new Customer(name, mail, password, creditCard);
        customerRepository.save(newcustomer);
        return newcustomer;
    }

    @Override
    public Optional<Customer> findCustomerByMail(String mail) {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .filter(cust -> mail.equals(cust.getMail())).findAny();
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findCustomerById(id);
    }

    @Override
    public Optional<Customer> findCustomerByName(String name) {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .filter(cust -> name.equals(cust.getName())).findAny();
    }

    @Override
    public Customer editVFP(Customer customer, LocalDate localDate) throws CustomerNotFoundException {
        Customer customerUpdatedVFP = customerRepository.findById(customer.getId()).orElseThrow(CustomerNotFoundException::new);
        customerUpdatedVFP.setVfp(localDate);
        customerRepository.save(customerUpdatedVFP);
        return customerUpdatedVFP;
    }

    @Override
    public Customer editBalance(Customer customer, double balanceChange) throws InsufficientBalanceException, CustomerNotFoundException {
        Optional<Customer> customerUpdatedBalance = customerRepository.findCustomerByMail(customer.getMail());
        if (customerUpdatedBalance.isPresent()) {
            customerUpdatedBalance.get().setBalance(customerUpdatedBalance.get().getBalance() + balanceChange);
            if (customerUpdatedBalance.get().getBalance() < 0)
                throw new InsufficientBalanceException();
            customerRepository.save(customerUpdatedBalance.get());
            return customerUpdatedBalance.get();
        }
        throw new CustomerNotFoundException();
    }

    @Override
    public Customer editFidelityPoints(Customer customer, int fidelityPointsBalanceChange) throws NegativePointCostException, CustomerNotFoundException {
        Optional<Customer> customerUpdatedFidelityPoints = customerRepository.findCustomerByMail(customer.getMail());
        if (customerUpdatedFidelityPoints.isPresent()) {
            customerUpdatedFidelityPoints.get().setFidelityPoints(customerUpdatedFidelityPoints.get().getFidelityPoints() + fidelityPointsBalanceChange);
            if (customerUpdatedFidelityPoints.get().getFidelityPoints() < 0)
                throw new NegativePointCostException();
            customerRepository.save(customerUpdatedFidelityPoints.get());
            return customerUpdatedFidelityPoints.get();
        }
        throw new CustomerNotFoundException();
    }

    @Override
    public Customer recordMatriculation(Customer customer, String matriculation) throws CustomerNotFoundException {
        Optional<Customer> customerUpdatedMatriculation = customerRepository.findCustomerByMail(customer.getMail());
        if (customerUpdatedMatriculation.isPresent()) {
            customerUpdatedMatriculation.get().setMatriculation(matriculation);
            customerRepository.save(customerUpdatedMatriculation.get());
            return customerUpdatedMatriculation.get();
        }
        throw new CustomerNotFoundException();
    }

    @Override
    public Customer recordCreditCard(Customer customer, String creditCard) throws CustomerNotFoundException {
        Optional<Customer> customerUpdatedCreditCard = customerRepository.findCustomerByMail(customer.getMail());
        if (customerUpdatedCreditCard.isPresent()) {
            customerUpdatedCreditCard.get().setCreditCard(creditCard);
            customerRepository.save(customerUpdatedCreditCard.get());
            return customerUpdatedCreditCard.get();
        }
        throw new CustomerNotFoundException();

    }

    @Override
    public Customer recordNewFavoriteStore(Customer customer, Store store) throws CustomerNotFoundException, StoreAlreadyRegisteredException {
        Optional<Customer> customerUpdatedFavoriteStore = customerRepository.findCustomerByName(customer.getName());
        if (customerUpdatedFavoriteStore.isPresent()) {
            Optional<Store> storeToBeAdded = customerUpdatedFavoriteStore.get().getFavoriteStores().stream().filter(s -> s.getId().equals(store.getId())).findAny();
            if (storeToBeAdded.isEmpty()) {
                customerUpdatedFavoriteStore.get().getFavoriteStores().add(store);
                customerRepository.save(customerUpdatedFavoriteStore.get());
                return customerUpdatedFavoriteStore.get();
            }
            throw new StoreAlreadyRegisteredException();
        }
        throw new CustomerNotFoundException();

    }

    @Override
    public Customer removeFavoriteStore(Customer customer, Store store) throws StoreNotFoundException, CustomerNotFoundException {
        Optional<Customer> customerUpdatedFavoriteStore = customerRepository.findCustomerByName(customer.getName());
        if (customerUpdatedFavoriteStore.isPresent()) {
            Optional<Store> storeToBeRemoved = customerUpdatedFavoriteStore.get().getFavoriteStores().stream().filter(s -> s.getId().equals(store.getId())).findAny();
            if (storeToBeRemoved.isPresent()) {
                customerUpdatedFavoriteStore.get().getFavoriteStores().remove(storeToBeRemoved.get());
                customerRepository.save(customerUpdatedFavoriteStore.get());
                return customerUpdatedFavoriteStore.get();
            }
            throw new StoreNotFoundException();
        }
        throw new CustomerNotFoundException();
    }

    @Override
    public Customer recordNewFavoriteStores(Customer customer, Set<Store> store) throws StoreAlreadyRegisteredException, CustomerNotFoundException {
        Optional<Customer> customerUpdatedFavoriteStore = customerRepository.findCustomerByName(customer.getName());
        if (customerUpdatedFavoriteStore.isPresent()) {
            for (Store s : store) {
                recordNewFavoriteStore(customerUpdatedFavoriteStore.get(), s);
            }
            customerRepository.save(customerUpdatedFavoriteStore.get());
            return customerUpdatedFavoriteStore.get();
        }
        throw new CustomerNotFoundException();
    }


    @Override
    public Customer removeAllFavoriteStores(Customer customer, Set<Store> store) throws CustomerNotFoundException {
        Optional<Customer> customerUpdatedFavoriteStore = customerRepository.findCustomerByName(customer.getName());
        if (customerUpdatedFavoriteStore.isPresent()) {
            for (Store s : store) {
                Optional<Store> storeToBeRemoved = customerUpdatedFavoriteStore.get().getFavoriteStores().stream().filter(st -> st.getId().equals(s.getId())).findAny();
                storeToBeRemoved.ifPresent(value -> customerUpdatedFavoriteStore.get().getFavoriteStores().remove(value));
            }
            customerRepository.save(customerUpdatedFavoriteStore.get());
            return customerUpdatedFavoriteStore.get();
        }
        throw new CustomerNotFoundException();
    }

    //TODO delete Customer --> delete les purchases liés au customer avant de delete le customer (acces aux 2 repositories)
}
