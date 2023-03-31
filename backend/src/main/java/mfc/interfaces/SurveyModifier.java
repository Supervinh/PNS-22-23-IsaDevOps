package mfc.interfaces;

import mfc.entities.Customer;
import mfc.entities.Survey;
import mfc.exceptions.AlreadyAnsweredException;
import mfc.exceptions.InvalidAnswerException;
import mfc.exceptions.SurveyAlreadyExistsException;
import mfc.exceptions.SurveyNotFoundException;

public interface SurveyModifier {
    Survey answerSurvey(Survey survey, String answer, Customer customer) throws SurveyNotFoundException, InvalidAnswerException, AlreadyAnsweredException;

    Survey createSurvey(Survey survey) throws SurveyAlreadyExistsException;

    void deleteSurvey(Survey survey) throws SurveyNotFoundException;
}
