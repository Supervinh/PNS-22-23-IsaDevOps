package mfc.controllers.dto;

import java.util.Map;
import java.util.Objects;

public class SurveyDTO {
    private String name;
    private String question;
    private Map<String, Integer> answers;

    public SurveyDTO(String name, String question) {
        this.name = name;
        this.question = question;
    }

    public SurveyDTO(String name, String question, Map<String, Integer> answers) {
        this.name = name;
        this.question = question;
        this.answers = answers;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SurveyDTO surveyDTO)) return false;
        return Objects.equals(name, surveyDTO.name) && Objects.equals(question, surveyDTO.question) && Objects.equals(answers, surveyDTO.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, question, answers);
    }

    @Override
    public String toString() {
        return "SurveyDTO{" +
                "name='" + name + '\'' +
                ", question='" + question + '\'' +
                ", answers=" + answers +
                '}';
    }
}
