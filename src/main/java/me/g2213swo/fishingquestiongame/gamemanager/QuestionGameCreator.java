package me.g2213swo.fishingquestiongame.gamemanager;

import me.g2213swo.fishingquestiongame.FishingQuestionGame;
import me.g2213swo.fishingquestiongame.quiz.MultipleChoiceQuiz;
import me.g2213swo.fishingquestiongame.quiz.Quiz;
import me.g2213swo.fishingquestiongame.quiz.QuizType;
import me.g2213swo.fishingquestiongame.quiz.TrueOrFalseQuiz;
import net.momirealms.customfishing.api.CustomFishingPlugin;
import net.momirealms.customfishing.api.manager.GameManager;
import net.momirealms.customfishing.api.mechanic.game.Game;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

/**
 *  创建一个实现GameManager.GameCreator的类
 */
public class QuestionGameCreator implements GameManager.GameCreator {
    private final FishingQuestionGame plugin;

    private final CustomFishingPlugin customFishingPlugin;

    private final Set<Quiz> quizSet;

    public QuestionGameCreator(FishingQuestionGame plugin, CustomFishingPlugin customFishingPlugin) {
        this.plugin = plugin;
        this.customFishingPlugin = customFishingPlugin;
        this.quizSet = new HashSet<>();

        // 注册内容
        customFishingPlugin.getGameManager().registerGameType("question-game", this);
    }

    @Override
    public Game setArgs(ConfigurationSection configurationSection) {
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

        int randomIndex = new Random().nextInt(quizSet.size());
        Quiz quiz = quizSet.toArray(new Quiz[0])[randomIndex];
        return new QuestionGame(configurationSection, quiz);
    }

    public void unload() {
        customFishingPlugin.getGameManager().unregisterGameType("chat-game");
    }
}
