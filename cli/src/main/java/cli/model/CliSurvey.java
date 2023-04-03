package cli.model;

import java.util.Map;

public class CliSurvey {
    private String name;
    private String question;
    private Map<String, Integer> answers;

    public CliSurvey(String name, String question) {
        this.name = name;
        this.question = question;
        answers = Map.of();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<String, Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, Integer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "CliSurvey{" +
                "name='" + name + '\'' +
                ", question='" + question + '\'' +
                ", answers=" + answers +
                '}';
    }
}
