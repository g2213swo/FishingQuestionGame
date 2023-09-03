package me.g2213swo.fishingquestiongame.quiz;

import java.util.List;

public record MultipleChoiceQuiz(String question, List<String> answers) implements Quiz {
    @Override
    public QuizType type() {
        return QuizType.MULTIPLE_CHOICE;
    }

    @Override
    public boolean isRightAnswer(String answer) {
        for (String s : answers) {
            if (s.equalsIgnoreCase(answer)) {
                return true;
            }
        }
        return false;
    }
}
