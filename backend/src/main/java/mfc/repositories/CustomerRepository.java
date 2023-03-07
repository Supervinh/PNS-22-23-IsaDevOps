package mfc.repositories;

import mfc.POJO.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByName(String name);
    Optional<Customer> findCustomerByMail(String mail);
    Optional<Customer> findCustomerById(Long id);

}
//@Repository
//public class CustomerRepository extends BasicRepositoryImpl<Customer, UUID> {
//    public Optional<Customer> findByMail(String mail) {
//        return StreamSupport.stream(findAll().spliterator(), false)
//                .filter(customer -> customer.getMail().equals(mail))
//                .findFirst();
//    }
//}
