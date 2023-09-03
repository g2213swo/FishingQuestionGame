package me.g2213swo.fishingquestiongame.gamemanager;

import me.g2213swo.fishingquestiongame.quiz.Quiz;
import me.g2213swo.fishingquestiongame.quiz.QuizType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import net.momirealms.customfishing.api.mechanic.game.AbstractGamingPlayer;
import net.momirealms.customfishing.api.mechanic.game.Game;
import net.momirealms.customfishing.api.mechanic.game.GameSettings;
import net.momirealms.customfishing.api.mechanic.game.GamingPlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;

/**
 * 具体Game的实现
 */
public class QuestionGame implements Game {
    private final ConfigurationSection section;

    private final Quiz quiz;

    public QuestionGame(ConfigurationSection section, Quiz quiz) {
        this.section = section;
        this.quiz = quiz;
    }

    @Override
    public GamingPlayer start(Player player, FishHook fishHook, GameSettings gameSettings) {
        return new AbstractGamingPlayer(player, fishHook, gameSettings) {
            private final Title title;
            {
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
                // 从超类继承的run()方法
                super.run();
                player.showTitle(title);
            }

            @Override
            public boolean onJump() {
                if (quiz.type() != QuizType.TRUE_OR_FALSE) {
                    return false;
                }
                if (!quiz.isRightAnswer("")) {
                    success = true;
                    endGame();
                    return true;
                }
                success = false;
                endGame();
                return true;
            }

            @Override
            public boolean onRightClick() {
                if (quiz.type() != QuizType.TRUE_OR_FALSE) {
                    return false;
                }

                if (quiz.isRightAnswer("")) {
                    success = true;
                    endGame();
                    return true;
                }
                success = false;
                endGame();
                return true;
            }

            @Override
            public boolean onChat(String message) {
                if (quiz.type() != QuizType.MULTIPLE_CHOICE) {
                    return false;
                }

                if (quiz.isRightAnswer(message)) {
                    success = true;
                    endGame();
                    return true;
                }
                return false;
            }
        };
    }

}
