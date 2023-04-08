package mfc.components.registries;

import mfc.entities.Customer;
import mfc.entities.Survey;
import mfc.exceptions.AlreadyAnsweredException;
import mfc.exceptions.AlreadyExistingSurveyException;
import mfc.exceptions.InvalidAnswerException;
import mfc.exceptions.SurveyNotFoundException;
import mfc.interfaces.explorer.SurveyFinder;
import mfc.interfaces.modifier.SurveyModifier;
import mfc.repositories.SurveyRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<Survey> findByCustomerDidntAnswered(Customer customer) {
        return surveyRepository.findAll().stream().filter(e -> !e.getCustomersAnswered().contains(customer)).collect(Collectors.toSet());
    }

    @Override
    public Survey answerSurvey(Survey survey, String answer, Customer customer) throws SurveyNotFoundException, InvalidAnswerException, AlreadyAnsweredException {
        surveyRepository.findByName(survey.getName()).orElseThrow(SurveyNotFoundException::new);
        if (survey.getCustomersAnswered().contains(customer)) {
            throw new AlreadyAnsweredException();
        }
        if (survey.getAnswers().containsKey(answer)) {
            survey.getAnswers().put(answer, survey.getAnswers().get(answer) + 1);
            survey.getCustomersAnswered().add(customer);
            surveyRepository.save(survey);
            return survey;
        } else {
            throw new InvalidAnswerException();
        }
    }

    @Override
    public Survey createSurvey(Survey survey) throws AlreadyExistingSurveyException {
        if (surveyRepository.findByName(survey.getName()).isPresent()) {
            throw new AlreadyExistingSurveyException();
        }
        return surveyRepository.save(survey);
    }

    @Override
    public void deleteSurvey(Survey survey) throws SurveyNotFoundException {
        if (surveyRepository.deleteByName(survey.getName()) == 0) {
            throw new SurveyNotFoundException();
        }
    }
}
