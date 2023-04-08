package mfc.repositories;

import mfc.entities.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    long deleteByName(String name);

    Optional<Survey> findByName(String name);
}