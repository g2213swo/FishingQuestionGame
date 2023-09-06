package me.g2213swo.fishingquestiongame;

import me.g2213swo.fishingquestiongame.gamemanager.QuestionGameFactory;
import net.momirealms.customfishing.api.CustomFishingPlugin;
import net.momirealms.customfishing.api.mechanic.game.GameExpansion;
import net.momirealms.customfishing.api.mechanic.game.GameFactory;

public class FishingQuestionGameExpansion extends GameExpansion {
    @Override
    public String getVersion() {
        return "0.0.2";
    }

    @Override
    public String getAuthor() {
        return "g2213swo";
    }

    @Override
    public String getGameType() {
        return "question-game";
    }

    @Override
    public GameFactory getGameFactory() {
        return new QuestionGameFactory(CustomFishingPlugin.get());
    }
}
