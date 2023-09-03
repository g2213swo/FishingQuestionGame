package me.g2213swo.fishingquestiongame.quiz;

public enum QuizType {
    TRUE_OR_FALSE,
    MULTIPLE_CHOICE;

    public static QuizType fromString(String string) {
        return switch (string.toLowerCase()) {
            case "true_or_false" -> TRUE_OR_FALSE;
            case "multiple_choice" -> MULTIPLE_CHOICE;
            default -> throw new IllegalArgumentException("Unknown quiz type: " + string);
        };
    }
}
