package me.g2213swo.fishingquestiongame.gamemanager;

import me.g2213swo.fishingquestiongame.quiz.MultipleChoiceQuiz;
import me.g2213swo.fishingquestiongame.quiz.Quiz;
import me.g2213swo.fishingquestiongame.quiz.QuizType;
import me.g2213swo.fishingquestiongame.quiz.TrueOrFalseQuiz;
import net.momirealms.customfishing.api.CustomFishingPlugin;
import net.momirealms.customfishing.api.mechanic.game.GameFactory;
import net.momirealms.customfishing.api.mechanic.game.GameInstance;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 创建一个实现GameFactory的类
 */
public class QuestionGameFactory implements GameFactory {
    private final CustomFishingPlugin plugin;

    private final Set<Quiz> quizSet;

    public QuestionGameFactory(CustomFishingPlugin plugin) {
        this.plugin = plugin;
        this.quizSet = new HashSet<>();
    }

    @Override
    public GameInstance setArgs(ConfigurationSection configurationSection) {
        List<Map<?, ?>> chatGames = configurationSection.getMapList("question-game");

        for (Map<?, ?> chatGame : chatGames) {
            String type = (String) chatGame.get("type");
            if (type == null) {
                plugin.getLogger().warning("Type is null");
                continue;
            }

            QuizType quizType = QuizType.fromString(type);
            String question = (String) chatGame.get("question");
            if (question == null) {
                plugin.getLogger().warning("Question is null");
                continue;
            }

            Object rightAnswer = chatGame.get("right");

            switch (quizType) {
                case TRUE_OR_FALSE -> {
                    if (!(rightAnswer instanceof Boolean booleanRightAnswer)) {
                        plugin.getLogger().warning("Right answer is not a boolean");
                        continue;
                    }
                    quizSet.add(new TrueOrFalseQuiz(question, booleanRightAnswer));
                }
                case MULTIPLE_CHOICE -> {
                    if (rightAnswer == null) {
                        plugin.getLogger().warning("Choices is null");
                        continue;
                    }
                    if (!(rightAnswer instanceof List<?> listRightAnswer)) {
                        plugin.getLogger().warning("Choices is not a list");
                        continue;
                    }
                    if (!(listRightAnswer.get(0) instanceof String)) {
                        plugin.getLogger().warning("Choices is not a list of string");
                        continue;
                    }

                    quizSet.add(new MultipleChoiceQuiz(question, (List<String>) listRightAnswer));
                }

                default -> plugin.getLogger().warning("Unknown quiz type: " + quizType);
            }
        }

        if (quizSet.isEmpty()) {
            plugin.getLogger().warning("No quiz found");
        }
        plugin.getLogger().info("Loaded " + quizSet.size() + " quiz");

        return new QuestionGame(configurationSection, quizSet);
    }
}
