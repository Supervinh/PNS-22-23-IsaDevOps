package mfc.repositories;

import mfc.POJO.Customer;
import repositories.BasicRepositoryImpl;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

public class CustomerRepository extends BasicRepositoryImpl<Customer, UUID> {
    public Optional<Customer> findByMail(String mail) {
        return StreamSupport.stream(findAll().spliterator(), false)
                .filter(customer -> customer.getMail().equals(mail))
                .findFirst();
    }
}
