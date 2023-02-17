package mfc.components;


import mfc.POJO.Customer;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.exceptions.CustomerNotFoundException;
import mfc.exceptions.InsufficientBalanceException;
import mfc.exceptions.NegativePointCostException;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.modifier.CustomerBalancesModifier;
import mfc.interfaces.modifier.CustomerRegistration;
import mfc.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Component
public class CustomerRegistry implements CustomerRegistration, CustomerFinder, CustomerBalancesModifier {

    private final CustomerRepository customerRepository;

    @Autowired // annotation is optional since Spring 4.3 if component has only one constructor
    public CustomerRegistry(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer register(String name, String mail, String password, String creditCard) throws AlreadyExistingAccountException {
        if (findCustomerByName(name).isPresent()) throw new AlreadyExistingAccountException();
        Customer newcustomer = new Customer(name, mail, password, creditCard);
        customerRepository.save(newcustomer, newcustomer.getId());
        return newcustomer;
    }

    @Override
    public Optional<Customer> findCustomerByName(String name) {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false).filter(cust -> name.equals(cust.getName())).findAny();
    }

    @Override
    public Optional<Customer> findCustomerById(UUID id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> findCustomerByMail(String mail) {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false).filter(cust -> mail.equals(cust.getMail())).findAny();
    }

    @Override
    public Customer editBalance(Customer customer, double balanceChange) throws InsufficientBalanceException, CustomerNotFoundException {
        Customer res = customerRepository.findById(customer.getId()).orElseThrow(CustomerNotFoundException::new);
        res.setBalance(res.getBalance() + balanceChange);
        customerRepository.save(res, res.getId());
        return res;
    }

    @Override
    public Customer editFidelityPoints(Customer customer, int fidelityPointsBalanceChange) throws NegativePointCostException, CustomerNotFoundException {
        Customer res = customerRepository.findById(customer.getId()).orElseThrow(CustomerNotFoundException::new);
        res.setFidelityPoints(res.getFidelityPoints() + fidelityPointsBalanceChange);
        customerRepository.save(res, res.getId());
        return res;
    }

}
