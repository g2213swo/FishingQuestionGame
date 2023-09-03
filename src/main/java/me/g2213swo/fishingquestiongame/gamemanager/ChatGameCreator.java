package me.g2213swo.fishingquestiongame.gamemanager;

import me.g2213swo.fishingquestiongame.FishingQuestionGame;
import net.momirealms.customfishing.api.CustomFishingPlugin;
import net.momirealms.customfishing.api.manager.GameManager;
import net.momirealms.customfishing.api.mechanic.game.Game;
import org.bukkit.configuration.ConfigurationSection;

/**
 *  创建一个实现GameManager.GameCreator的类
 */
public class ChatGameCreator implements GameManager.GameCreator {
    private final FishingQuestionGame plugin;

    private final CustomFishingPlugin customFishingPlugin;

    public ChatGameCreator(FishingQuestionGame plugin, CustomFishingPlugin customFishingPlugin) {
        this.plugin = plugin;
        this.customFishingPlugin = customFishingPlugin;

        // 注册内容
        customFishingPlugin.getGameManager().registerGameType("chat-game", this);
    }

    @Override
    public Game setArgs(ConfigurationSection configurationSection) {
        return new ChatGame(plugin, configurationSection);
    }

    public void unload() {
        customFishingPlugin.getGameManager().unregisterGameType("chat-game");
    }
}
