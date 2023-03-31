package mfc.interfaces;

import mfc.entities.Survey;

import java.util.Optional;
import java.util.Set;

public interface SurveyFinder {

    Optional<Survey> findByName(String name);

    Set<Survey> getSurveys();
}
