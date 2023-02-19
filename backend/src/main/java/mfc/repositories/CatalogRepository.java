package mfc.repositories;


import mfc.POJO.PayOff;
import org.springframework.stereotype.Repository;

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
}