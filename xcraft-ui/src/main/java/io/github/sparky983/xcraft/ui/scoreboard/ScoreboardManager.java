package io.github.sparky983.xcraft.ui.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;

/**
 * менеджер скорбордів для відображення інформації
 *
 * @author Андрій Будильников
 */
public class ScoreboardManager {

    private final Map<Player, Scoreboard> playerScoreboards;
    private final Map<Player, Objective> playerObjectives;

    public ScoreboardManager() {
        this.playerScoreboards = new HashMap<>();
        this.playerObjectives = new HashMap<>();
    }

    /**
     * створює скорборд для гравця
     *
     * @param player гравець
     * @param title назва
     * @return скорборд
     */
    public Scoreboard createScoreboard(Player player, String title) {
        if (player == null || title == null) {
            return null;
        }
        
        org.bukkit.scoreboard.ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        if (scoreboardManager == null) {
            return null;
        }
        
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        
        Objective objective = scoreboard.registerNewObjective("board", "dummy", title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        
        playerScoreboards.put(player, scoreboard);
        playerObjectives.put(player, objective);
        
        player.setScoreboard(scoreboard);
        return scoreboard;
    }

    /**
     * встановлює значення в скорборді для гравця
     *
     * @param player гравець
     * @param line номер рядка
     * @param text текст
     */
    public void setScoreboardLine(Player player, int line, String text) {
        Objective objective = playerObjectives.get(player);
        if (objective != null) {
            objective.getScore(text).setScore(line);
        }
    }

    /**
     * оновлює назву скорборду для гравця
     *
     * @param player гравець
     * @param title назва
     */
    public void updateScoreboardTitle(Player player, String title) {
        Objective objective = playerObjectives.get(player);
        if (objective != null) {
            objective.setDisplayName(title);
        }
    }

    /**
     * видаляє скорборд для гравця
     *
     * @param player гравець
     */
    public void removeScoreboard(Player player) {
        if (player == null) {
            return;
        }
        
        playerScoreboards.remove(player);
        playerObjectives.remove(player);
        
        org.bukkit.scoreboard.ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        if (scoreboardManager != null) {
            player.setScoreboard(scoreboardManager.getMainScoreboard());
        }
    }

    /**
     * отримує скорборд для гравця
     *
     * @param player гравець
     * @return скорборд або null
     */
    public Scoreboard getScoreboard(Player player) {
        return playerScoreboards.get(player);
    }

    /**
     * очищує всі скорборди
     */
    public void clearAllScoreboards() {
        org.bukkit.scoreboard.ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        
        if (scoreboardManager != null) {
            for (Player player : playerScoreboards.keySet()) {
                player.setScoreboard(scoreboardManager.getMainScoreboard());
            }
        }
        
        playerScoreboards.clear();
        playerObjectives.clear();
    }
}