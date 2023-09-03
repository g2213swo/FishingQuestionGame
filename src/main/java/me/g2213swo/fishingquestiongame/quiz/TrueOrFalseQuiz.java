package me.g2213swo.fishingquestiongame.quiz;

public record TrueOrFalseQuiz(String question, boolean rightQuestion) implements Quiz {

    @Override
    public QuizType type() {
        return QuizType.TRUE_OR_FALSE;
    }

    @Override
    public boolean isRightAnswer(String answer) {
        return rightQuestion;
    }
}
