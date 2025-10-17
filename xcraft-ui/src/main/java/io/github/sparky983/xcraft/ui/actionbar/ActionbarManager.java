package io.github.sparky983.xcraft.ui.actionbar;

import org.bukkit.entity.Player;

/**
 * менеджер екшнбару для відображення повідомлень внизу екрану
 *
 * @author Андрій Будильников
 */
public class ActionbarManager {

    /**
     * відображає повідомлення в екшнбарі
     *
     * @param player гравець
     * @param message повідомлення
     */
    public void sendActionbar(Player player, String message) {
        // використовуємо spigot api для відображення екшнбару
        player.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR, 
                                   net.md_5.bungee.api.chat.TextComponent.fromLegacyText(message));
    }

    /**
     * відображає успішне повідомлення в екшнбарі
     *
     * @param player гравець
     * @param message повідомлення
     */
    public void sendSuccessActionbar(Player player, String message) {
        sendActionbar(player, "§a" + message);
    }

    /**
     * відображає повідомлення про помилку в екшнбарі
     *
     * @param player гравець
     * @param message повідомлення
     */
    public void sendErrorActionbar(Player player, String message) {
        sendActionbar(player, "§c" + message);
    }

    /**
     * відображає інформаційне повідомлення в екшнбарі
     *
     * @param player гравець
     * @param message повідомлення
     */
    public void sendInfoActionbar(Player player, String message) {
        sendActionbar(player, "§e" + message);
    }

    /**
     * очищує екшнбар
     *
     * @param player гравець
     */
    public void clearActionbar(Player player) {
        sendActionbar(player, "");
    }
}