package mfc.components.registries;

import mfc.entities.Customer;
import mfc.entities.Store;
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
import java.time.LocalDateTime;
import java.util.Optional;

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
    public Customer delete(Customer customer) throws NoCorrespongingAccountException {
        if (findCustomerByName(customer.getName()).isEmpty()) throw new NoCorrespongingAccountException();
        customerRepository.delete(customer);
        return customer;
    }

    @Override
    public Optional<Customer> findCustomerByMail(String mail) {
        return customerRepository.findAll().stream().filter(cust -> mail.equals(cust.getMail())).findAny();
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findCustomerById(id);
    }
    
     @Override
    public Optional<Customer> findCustomerAtConnexion(String mail, String password) throws CredentialsException {
        Optional<Customer> customer = customerRepository.findCustomerByMail(mail);
        if (customer.isPresent() && customer.get().getPassword().equals(password)) {
            customer.get().setLastConnexion(LocalDateTime.now());
            customerRepository.save(customer.get());
        } else if (customer.isPresent()) {
            throw new CredentialsException();
        }
        return customer;
    }

    @Override
    public Optional<Customer> findCustomerByName(String name) {
        return customerRepository.findAll().stream().filter(cust -> name.equals(cust.getName())).findAny();
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
            if (customerUpdatedBalance.get().getBalance() < 0) throw new InsufficientBalanceException();
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
            if (customerUpdatedFidelityPoints.get().getFidelityPoints() < 0) throw new NegativePointCostException();
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
        Customer customerUpdatedFavoriteStore = customerRepository.findCustomerByName(customer.getName()).orElseThrow(CustomerNotFoundException::new);
        if (customerUpdatedFavoriteStore.getFavoriteStores().stream().anyMatch(e -> e.equals(store)))
            throw new StoreAlreadyRegisteredException();
        customerUpdatedFavoriteStore.getFavoriteStores().add(store);
        customerRepository.save(customerUpdatedFavoriteStore);
        return customerUpdatedFavoriteStore;
    }

    @Override
    public Customer removeFavoriteStore(Customer customer, Store store) throws StoreNotFoundException, CustomerNotFoundException {
        Customer customerUpdatedFavoriteStore = customerRepository.findCustomerByName(customer.getName()).orElseThrow(CustomerNotFoundException::new);
        if (customerUpdatedFavoriteStore.getFavoriteStores().stream().noneMatch(e -> e.equals(store)))
            throw new StoreNotFoundException();
        customerUpdatedFavoriteStore.getFavoriteStores().remove(store);
        customerRepository.save(customerUpdatedFavoriteStore);
        return customerUpdatedFavoriteStore;
    }

}
