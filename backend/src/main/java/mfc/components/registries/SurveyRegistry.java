package mfc.components.registries;

import mfc.entities.Customer;
import mfc.entities.Survey;
import mfc.exceptions.AlreadyAnsweredException;
import mfc.exceptions.InvalidAnswerException;
import mfc.exceptions.SurveyNotFoundException;
import mfc.interfaces.SurveyFinder;
import mfc.interfaces.SurveyModifier;
import mfc.repositories.SurveyRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Component
@Transactional
public class SurveyRegistry implements SurveyFinder, SurveyModifier {

    private final SurveyRepository surveyRepository;

    public SurveyRegistry(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public Optional<Survey> findByName(String name) {
        return surveyRepository.findByName(name);
    }

    @Override
    public Set<Survey> getSurveys() {
        return surveyRepository.findAllSet();
    }

    @Override
    public Survey answerSurvey(Survey survey, String answer, Customer customer) throws SurveyNotFoundException, InvalidAnswerException, AlreadyAnsweredException {
        surveyRepository.findByName(survey.getName()).orElseThrow(SurveyNotFoundException::new);
        if (survey.getCustomersAnswered().contains(customer)) {
            throw new AlreadyAnsweredException();
        }
        if (survey.getAnswers().containsKey(answer)) {
            survey.getAnswers().put(answer, survey.getAnswers().get(answer) + 1);
            surveyRepository.save(survey);
            return survey;
        } else {
            throw new InvalidAnswerException();
        }
    }

    @Override
    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    @Override
    public void deleteSurvey(Survey survey) throws SurveyNotFoundException {
        if (surveyRepository.deleteByName(survey.getName()) == 0) {
            throw new SurveyNotFoundException();
        }
    }
}
