package ies.portadaalta.quizzengine.model;

import java.util.List;

public class Question {

    private final Category category;
    private final String question;
    // Right answer is always the first one
    private final List<String> answers;

    public Question(Category category, String question, List<String> answers) {
        this.category = category;
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }


    public Category getCategory() {
        return category;
    }

    public boolean isValidAnswer(String answer) {
        return answer.equals(answers.getFirst());
    }

    public String getRightAnswer() {
        return answers.getFirst();
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                ", rightAnswer=" + answers.getFirst() +
                '}';
    }

}
