package mfc.controllers;

import mfc.POJO.*;
import mfc.controllers.dto.AdminDTO;
import mfc.repositories.AdminRepository;
import mfc.repositories.CustomerRepository;
import mfc.repositories.PayoffPurchaseRepository;
import mfc.repositories.StoreRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class PayoffControllerIT {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PayoffPurchaseRepository payoffPurchaseRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void PayoffPurchaseIsDeletedOnStoreDeletion() throws Exception {
        Store store = new Store("Carrefour", null);
        store = storeRepository.saveAndFlush(store);
        Customer customer = new Customer("custo1", "1010101010");
        customer = customerRepository.saveAndFlush(customer);
        PayoffPurchase pop = payoffPurchaseRepository.save(new PayoffPurchase("low", 10,10, store, customer));
        System.out.println(payoffPurchaseRepository.findPayoffPurchaseById(pop.getId()));
        storeRepository.deleteById(store.getId());
        storeRepository.flush();
        assertEquals(payoffPurchaseRepository.findPayoffPurchaseById(pop.getId()), Optional.empty());
    }
}
