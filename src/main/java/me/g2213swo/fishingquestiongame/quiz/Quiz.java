package me.g2213swo.fishingquestiongame.quiz;

public interface Quiz {
    String question();

    QuizType type();

    boolean isRightAnswer(String answer);
}
