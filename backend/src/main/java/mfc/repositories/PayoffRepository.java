package mfc.repositories;


import mfc.entities.Payoff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public interface PayoffRepository extends JpaRepository<Payoff, Long> {
    public default Set<Payoff> explore(String name) {
        return StreamSupport.stream(findAll().spliterator(), false)
                .filter(payOff -> payOff.getName().contains(name))
                .collect(Collectors.toSet());
    }

    public default Set<Payoff> available(int pointCost) {
        return StreamSupport.stream(findAll().spliterator(), false)
                .filter(payOff -> payOff.getPointCost() <= pointCost)
                .collect(Collectors.toSet());
    }

    Optional<Payoff> findPayoffByNameAndStore_Name(String name, String store);
}