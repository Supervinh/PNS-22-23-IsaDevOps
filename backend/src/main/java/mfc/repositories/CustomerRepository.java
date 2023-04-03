package mfc.repositories;

import mfc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByName(String name);

    Optional<Customer> findCustomerByMail(String mail);

    Optional<Customer> findCustomerById(Long id);
}
