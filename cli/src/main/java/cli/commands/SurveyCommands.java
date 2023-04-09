package cli.commands;


import cli.CliContext;
import cli.model.CliSurvey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class SurveyCommands {
    public static final String BASE_URI = "/survey.txt";

    private final RestTemplate restTemplate;

    private final CliContext cliContext;

    @Autowired
    public SurveyCommands(RestTemplate restTemplate, CliContext cliContext) {
        this.restTemplate = restTemplate;
        this.cliContext = cliContext;
    }

    @ShellMethod("Answer a survey (answerSurvey SURVEY_NAME ANSWER)")
    public CliSurvey answerSurvey(String name, String answer) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return null;
        }
        return restTemplate.postForObject(getUri() + "/answer/" + name, answer, CliSurvey.class);
    }

    @ShellMethod("Show survey (showSurvey SURVEY_NAME)")
    public CliSurvey showSurvey(String name) {
        if (cliContext.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
            return null;
        }
        return restTemplate.getForObject(getUri() + "/show/" + name, CliSurvey.class);
    }

    @ShellMethod("Create survey (createSurvey SURVEY_NAME QUESTION)")
    public CliSurvey createSurvey(String name, String question) {
        return restTemplate.postForObject(getUri() + "/create", new CliSurvey(name, question), CliSurvey.class);
    }

    @ShellMethod("Get a survey (getSurvey SURVEY_NAME)")
    public CliSurvey getSurvey(String name) {
        return restTemplate.getForObject(getUri() + "/get/" + name, CliSurvey.class);
    }

    @ShellMethod("Delete  a survey (deleteSurvey SURVEY_NAME)")
    public void deleteSurvey(String name) {
        restTemplate.delete(getUri() + "/delete/" + name);
    }

    private String getUri() {
        return BASE_URI + "/" + cliContext.getLoggedInUser().getId();
    }


}
