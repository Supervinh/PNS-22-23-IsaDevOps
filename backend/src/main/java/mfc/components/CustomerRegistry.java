package mfc.components;


import mfc.POJO.Customer;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.modifier.CustomerRegistration;
import mfc.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Component
public class CustomerRegistry implements CustomerRegistration, CustomerFinder {

    private CustomerRepository customerRepository;

    @Autowired // annotation is optional since Spring 4.3 if component has only one constructor
    public CustomerRegistry(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer register(String name, String mail, String password, String creditCard)
            throws AlreadyExistingAccountException {
        if (findCustomerByName(name).isPresent())
            throw new AlreadyExistingAccountException();
        Customer newcustomer = new Customer(name, mail, password, creditCard);
        customerRepository.save(newcustomer, newcustomer.getId());
        return newcustomer;
    }

    @Override
    public Optional<Customer> findCustomerByName(String name) {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .filter(cust -> name.equals(cust.getName())).findAny();
    }

    @Override
    public Optional<Customer> findCustomerById(UUID id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> findCustomerByMail(String mail) {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .filter(cust -> mail.equals(cust.getMail())).findAny();
    }

}
