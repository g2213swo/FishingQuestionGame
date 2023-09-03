package me.g2213swo.fishingquestiongame.gamemanager;

import me.g2213swo.fishingquestiongame.FishingQuestionGame;
import me.g2213swo.fishingquestiongame.Quiz;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import net.momirealms.customfishing.api.manager.FishingManager;
import net.momirealms.customfishing.api.mechanic.game.AbstractGamingPlayer;
import net.momirealms.customfishing.api.mechanic.game.Game;
import net.momirealms.customfishing.api.mechanic.game.GameSettings;
import net.momirealms.customfishing.api.mechanic.game.GamingPlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * 具体Game的实现
 */
public class ChatGame implements Game {
    private final Set<Quiz> quizSet;

    private final ConfigurationSection section;

    public ChatGame(FishingQuestionGame plugin, ConfigurationSection section) {
        this.section = section;
        this.quizSet = new HashSet<>();

        List<Map<?, ?>> chatGames = this.section.getMapList("chat-game");

        for (Map<?, ?> chatGame : chatGames) {
            String question = (String) chatGame.get("question");
            if (question == null) {
                plugin.getLogger().warning("Question is null");
                continue;
            }

            Object rightAnswer = chatGame.get("right");
            if (!(rightAnswer instanceof Boolean booleanRightAnswer)) {
                plugin.getLogger().warning("Right answer is not a boolean");
                continue;
            }
            quizSet.add(new Quiz(question, booleanRightAnswer));
        }

        if (quizSet.isEmpty()) {
            plugin.getLogger().warning("No quiz found");
        }
    }

    @Override
    public GamingPlayer start(Player player, GameSettings gameSettings, FishingManager fishingManager) {
        return new AbstractGamingPlayer(player, gameSettings, fishingManager) {
            private final Quiz quiz;
            private final Title title;

            {
                int randomIndex = new Random().nextInt(quizSet.size());
                quiz = quizSet.toArray(new Quiz[0])[randomIndex];
                var titleString = section.getString("title");
                var subTitleString = section.getString("subtitle");

                titleString = titleString.replace("{question}", quiz.question());
                subTitleString = subTitleString.replace("{question}", quiz.question());

                Component titleComponent = MiniMessage.miniMessage().deserialize(titleString);
                Component subTitleComponent = MiniMessage.miniMessage().deserialize(subTitleString);

                title = Title.title(
                        titleComponent,
                        subTitleComponent,
                        Title.Times.times(
                                Ticks.duration(0),
                                Ticks.duration(500),
                                Ticks.duration(0)
                        )
                );
            }

            @Override
            public void run() {
                super.run();
                player.showTitle(title);
            }

            @Override
            public boolean onJump() {
                if (!quiz.isRight()) {
                    succeeded = true;
                    fishingManager.processGameResult(this);
                    return true;
                }
                succeeded = false;
                fishingManager.processGameResult(this);
                return true;
            }

            @Override
            public boolean onRightClick() {
                if (quiz.isRight()) {
                    succeeded = true;
                    fishingManager.processGameResult(this);
                    return true;
                }
                succeeded = false;
                fishingManager.processGameResult(this);
                return true;
            }
        };
    }

}
