package mfc.repositories;


import mfc.POJO.PayOff;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class CatalogRepository extends BasicRepositoryImpl<PayOff, UUID> {
    public Set<PayOff> explore(String name) {
        return StreamSupport.stream(findAll().spliterator(), false)
                .filter(payOff -> payOff.getName().contains(name))
                .collect(Collectors.toSet());
    }

    public Set<PayOff> available(int pointCost) {
        return StreamSupport.stream(findAll().spliterator(), false)
                .filter(payOff -> payOff.getPointCost() <= pointCost)
                .collect(Collectors.toSet());
    }

    /**
     * Search for a payoff by name and store name
     *
     * @param name  should be unique
     * @param name1 should be unique
     * @return the payoff if found, empty otherwise
     */
    public Optional<PayOff> findPayoff(String name, String name1) {
        return StreamSupport.stream(findAll().spliterator(), false)
                .filter(payOff -> payOff.getName().equals(name) && payOff.getStore().getName().equals(name1))
                .findAny();
    }
}