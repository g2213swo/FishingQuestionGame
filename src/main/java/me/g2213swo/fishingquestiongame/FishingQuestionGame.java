package me.g2213swo.fishingquestiongame;

import me.g2213swo.fishingquestiongame.gamemanager.QuestionGameCreator;
import net.momirealms.customfishing.api.CustomFishingPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class FishingQuestionGame extends JavaPlugin {

    private QuestionGameCreator questionGameCreator;

    private CustomFishingPlugin customFishingPlugin;

    @Override
    public void onLoad() {
        customFishingPlugin = CustomFishingPlugin.get();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        questionGameCreator = new QuestionGameCreator(this, customFishingPlugin);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        questionGameCreator.unload();
    }
}
