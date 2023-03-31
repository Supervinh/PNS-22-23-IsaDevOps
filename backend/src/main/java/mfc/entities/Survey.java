package mfc.entities;

import javax.persistence.*;
import java.util.*;

@Entity
public class Survey {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String question;
    @ElementCollection
    private Map<String, Integer> answers;
    @ManyToMany
    private List<Customer> customersAnswered;

    public Survey(String name, String question) {
        this.name = name;
        this.question = question;
        answers = new HashMap<>(Map.of("Yes", 0, "No", 0));
        customersAnswered = new ArrayList<>();
    }

    public Survey() {

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Customer> getCustomersAnswered() {
        return customersAnswered;
    }

    public void setCustomersAnswered(List<Customer> customersAnswered) {
        this.customersAnswered = customersAnswered;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "name='" + name + '\'' +
                ", question='" + question + '\'' +
                ", answers=" + answers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Survey survey)) return false;
        return Objects.equals(name, survey.name) && Objects.equals(question, survey.question) && Objects.equals(answers, survey.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, question, answers);
    }
}
