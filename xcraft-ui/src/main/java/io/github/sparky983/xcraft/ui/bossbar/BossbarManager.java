package io.github.sparky983.xcraft.ui.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * менеджер босбарів для відображення прогресу
 *
 * @author Андрій Будильников
 */
public class BossbarManager {

    private final Map<Player, BossBar> playerBossBars;

    public BossbarManager() {
        this.playerBossBars = new HashMap<>();
    }

    /**
     * створює босбар для гравця
     *
     * @param player гравець
     * @param title назва
     * @param progress прогрес (0.0 - 1.0)
     * @param color колір
     * @param style стиль
     * @return босбар
     */
    public BossBar createBossBar(Player player, String title, double progress, BarColor color, BarStyle style) {
        BossBar bossBar = Bukkit.createBossBar(title, color, style);
        bossBar.setProgress(progress);
        bossBar.addPlayer(player);
        playerBossBars.put(player, bossBar);
        return bossBar;
    }

    /**
     * створює босбар для гравця з стандартними параметрами
     *
     * @param player гравець
     * @param title назва
     * @param progress прогрес (0.0 - 1.0)
     * @return босбар
     */
    public BossBar createBossBar(Player player, String title, double progress) {
        return createBossBar(player, title, progress, BarColor.BLUE, BarStyle.SOLID);
    }

    /**
     * оновлює прогрес босбару для гравця
     *
     * @param player гравець
     * @param progress прогрес (0.0 - 1.0)
     */
    public void updateBossBarProgress(Player player, double progress) {
        BossBar bossBar = playerBossBars.get(player);
        if (bossBar != null) {
            bossBar.setProgress(Math.max(0.0, Math.min(1.0, progress)));
        }
    }

    /**
     * оновлює назву босбару для гравця
     *
     * @param player гравець
     * @param title назва
     */
    public void updateBossBarTitle(Player player, String title) {
        BossBar bossBar = playerBossBars.get(player);
        if (bossBar != null) {
            bossBar.setTitle(title);
        }
    }

    /**
     * видаляє босбар для гравця
     *
     * @param player гравець
     */
    public void removeBossBar(Player player) {
        BossBar bossBar = playerBossBars.remove(player);
        if (bossBar != null) {
            bossBar.removePlayer(player);
        }
    }

    /**
     * отримує босбар для гравця
     *
     * @param player гравець
     * @return босбар або null
     */
    public BossBar getBossBar(Player player) {
        return playerBossBars.get(player);
    }

    /**
     * очищує всі босбари
     */
    public void clearAllBossBars() {
        for (Map.Entry<Player, BossBar> entry : playerBossBars.entrySet()) {
            entry.getValue().removePlayer(entry.getKey());
        }
        playerBossBars.clear();
    }
}