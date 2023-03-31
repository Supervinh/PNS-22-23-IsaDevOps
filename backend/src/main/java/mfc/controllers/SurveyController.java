package mfc.controllers;

import mfc.controllers.dto.SurveyDTO;
import mfc.entities.Customer;
import mfc.entities.Survey;
import mfc.exceptions.*;
import mfc.interfaces.SurveyFinder;
import mfc.interfaces.SurveyModifier;
import mfc.interfaces.explorer.AdminFinder;
import mfc.interfaces.explorer.CustomerFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static mfc.controllers.dto.ConvertDTO.convertSurveyToDTO;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(path = StoreOwnerController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class SurveyController {

    public static final String BASE_URI = "/survey/";
    public static final String LOGGED_URI = "/{accountId}/";

    @Autowired
    private CustomerFinder customerFinder;

    @Autowired
    private SurveyFinder surveyFinder;

    @Autowired
    private SurveyModifier surveyModifier;

    @Autowired
    private AdminFinder adminFinder;

    @GetMapping(path = LOGGED_URI + "get/{surveyName}")
    public ResponseEntity<SurveyDTO> get(@PathVariable("accountId") Long accountId, @PathVariable String surveyName) throws CustomerNotFoundException, SurveyNotFoundException {
        customerFinder.findCustomerById(accountId).orElseThrow(CustomerNotFoundException::new);
        Survey survey = surveyFinder.findByName(surveyName).orElseThrow(SurveyNotFoundException::new);
        return ResponseEntity.ok(convertSurveyToDTO(survey));
    }

    @PostMapping(path = LOGGED_URI + "create")
    public ResponseEntity<SurveyDTO> create(@PathVariable("accountId") Long accountId, @RequestBody @Valid SurveyDTO surveyDTO) throws CredentialsException {
        adminFinder.findAdminById(accountId).orElseThrow(CredentialsException::new);
        Survey survey = surveyModifier.createSurvey(new Survey(surveyDTO.getName(), surveyDTO.getQuestion()));
        return ResponseEntity.ok(convertSurveyToDTO(survey));
    }

    @PostMapping(path = LOGGED_URI + "answer/{surveyName}")
    public ResponseEntity<SurveyDTO> answer(@PathVariable("accountId") Long accountId, @PathVariable String surveyName, @RequestBody String answer) throws CustomerNotFoundException, SurveyNotFoundException, InvalidAnswerException, AlreadyAnsweredException {
        Customer customer = customerFinder.findCustomerById(accountId).orElseThrow(CustomerNotFoundException::new);
        Survey survey = surveyFinder.findByName(surveyName).orElseThrow(SurveyNotFoundException::new);
        survey = surveyModifier.answerSurvey(survey, answer, customer);
        return ResponseEntity.ok(convertSurveyToDTO(survey));
    }

    @PostMapping(path = LOGGED_URI + "show/{surveyName}")
    public ResponseEntity<SurveyDTO> show(@PathVariable("accountId") Long accountId, @PathVariable String surveyName) throws CustomerNotFoundException, SurveyNotFoundException {
        customerFinder.findCustomerById(accountId).orElseThrow(CustomerNotFoundException::new);
        Survey survey = surveyFinder.findByName(surveyName).orElseThrow(SurveyNotFoundException::new);
        SurveyDTO surveyDTO = convertSurveyToDTO(survey);
        Map<String, Integer> answers = surveyDTO.getAnswers();
        answers.forEach((k, v) -> answers.put(k, 0));
        surveyDTO.setAnswers(answers);
        return ResponseEntity.ok(surveyDTO);
    }

    @DeleteMapping(path = LOGGED_URI + "delete/{surveyName}")
    public ResponseEntity<Void> delete(@PathVariable("accountId") Long accountId, @PathVariable String surveyName) throws CredentialsException, SurveyNotFoundException {
        adminFinder.findAdminById(accountId).orElseThrow(CredentialsException::new);
        Survey survey = surveyFinder.findByName(surveyName).orElseThrow(SurveyNotFoundException::new);
        surveyModifier.deleteSurvey(survey);
        return ResponseEntity.ok().build();
    }

}
