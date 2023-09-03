package me.g2213swo.fishingquestiongame;

import me.g2213swo.fishingquestiongame.gamemanager.ChatGameCreator;
import net.momirealms.customfishing.api.CustomFishingPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class FishingQuestionGame extends JavaPlugin {

    private ChatGameCreator chatGameCreator;

    private CustomFishingPlugin customFishingPlugin;

    @Override
    public void onLoad() {
        customFishingPlugin = CustomFishingPlugin.get();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        chatGameCreator = new ChatGameCreator(this, customFishingPlugin);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        chatGameCreator.unload();
    }
}
