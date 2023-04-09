package mfc.interfaces.modifier;

import mfc.entities.Customer;
import mfc.entities.Survey;
import mfc.exceptions.AlreadyAnsweredException;
import mfc.exceptions.AlreadyExistingSurveyException;
import mfc.exceptions.InvalidAnswerException;
import mfc.exceptions.SurveyNotFoundException;

public interface SurveyModifier {
    Survey answerSurvey(Survey survey, String answer, Customer customer) throws SurveyNotFoundException, InvalidAnswerException, AlreadyAnsweredException;

    Survey createSurvey(Survey survey) throws AlreadyExistingSurveyException;

    void deleteSurvey(Survey survey) throws SurveyNotFoundException;
}
