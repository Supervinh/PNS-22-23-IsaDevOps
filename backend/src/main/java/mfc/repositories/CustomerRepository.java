package mfc.repositories;

import mfc.POJO.Customer;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CustomerRepository extends BasicRepositoryImpl<Customer, UUID> {

}
