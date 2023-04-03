package mfc.repositories;

import mfc.entities.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    long deleteByName(String name);

    Optional<Survey> findByName(String name);

    default Set<Survey> findAllSet() {
        return new HashSet<>(findAll());
    }
}