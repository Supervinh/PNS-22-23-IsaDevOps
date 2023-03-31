package mfc.interfaces;

import mfc.entities.Customer;
import mfc.entities.Survey;
import mfc.exceptions.AlreadyAnsweredException;
import mfc.exceptions.InvalidAnswerException;
import mfc.exceptions.SurveyNotFoundException;

public interface SurveyModifier {
    Survey answerSurvey(Survey survey, String answer, Customer customer) throws SurveyNotFoundException, InvalidAnswerException, AlreadyAnsweredException;

    Survey createSurvey(Survey survey);

    void deleteSurvey(String name) throws SurveyNotFoundException;
}
