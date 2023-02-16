package mfc.repositories;

import mfc.POJO.PayOff;
import org.springframework.stereotype.Repository;
import repositories.BasicRepositoryImpl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Repository
public class CatalogRepository extends BasicRepositoryImpl<PayOff, UUID> {
    public Set<PayOff> explore(String name){
        Set<PayOff> payOffSet = new HashSet<>();
        StreamSupport.stream(findAll().spliterator(), false)
                .filter(payOff -> payOff.getName().contains(name))
                .forEach(payOff -> payOffSet.add(payOff));
        return payOffSet;
    }

    public Set<PayOff> available(int pointCost){
        Set<PayOff> payOffSet = new HashSet<>();
        StreamSupport.stream(findAll().spliterator(), false)
                .filter(payOff -> payOff.getPointCost() <= pointCost)
                .forEach(payOff -> payOffSet.add(payOff));
        return payOffSet;
    }
}
