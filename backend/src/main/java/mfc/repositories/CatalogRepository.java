package mfc.repositories;


import mfc.pojo.Payoff;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class CatalogRepository extends BasicRepositoryImpl<Payoff, UUID> {
    public Set<Payoff> explore(String name) {
        return StreamSupport.stream(findAll().spliterator(), false)
                .filter(payOff -> payOff.getName().contains(name))
                .collect(Collectors.toSet());
    }

    public Set<Payoff> available(int pointCost) {
        return StreamSupport.stream(findAll().spliterator(), false)
                .filter(payOff -> payOff.getPointCost() <= pointCost)
                .collect(Collectors.toSet());
    }

    /**
     * Search for a payoff by name and store name
     *
     * @param name      should be unique
     * @param storeName should be unique
     * @return the payoff if found, empty otherwise
     */
    public Optional<Payoff> findPayoff(String name, String storeName) {
        return StreamSupport.stream(findAll().spliterator(), false)
                .filter(payOff -> payOff.getName().equals(name) && payOff.getStore().getName().equals(storeName))
                .findAny();
    }
}