package io.github.sparky983.xcraft.ui.title;

import org.bukkit.entity.Player;

/**
 * менеджер тайтлів для відображення заголовків і підзаголовків
 *
 * @author Андрій Будильников
 */
public class TitleManager {

    /**
     * відображає тайтл гравцю
     *
     * @param player гравець
     * @param title заголовок
     * @param subtitle підзаголовок
     */
    public void sendTitle(Player player, String title, String subtitle) {
        player.sendTitle(title, subtitle, 10, 70, 20);
    }

    /**
     * відображає тайтл гравцю з кастомними параметрами
     *
     * @param player гравець
     * @param title заголовок
     * @param subtitle підзаголовок
     * @param fadeIn час появи
     * @param stay час показу
     * @param fadeOut час зникнення
     */
    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    /**
     * відображає тільки заголовок
     *
     * @param player гравець
     * @param title заголовок
     */
    public void sendTitle(Player player, String title) {
        sendTitle(player, title, "");
    }

    /**
     * відображає тільки підзаголовок
     *
     * @param player гравець
     * @param subtitle підзаголовок
     */
    public void sendSubtitle(Player player, String subtitle) {
        sendTitle(player, "", subtitle);
    }

    /**
     * очищує тайтл для гравця
     *
     * @param player гравець
     */
    public void clearTitle(Player player) {
        player.resetTitle();
    }

    /**
     * відображає успішний тайтл
     *
     * @param player гравець
     * @param message повідомлення
     */
    public void sendSuccessTitle(Player player, String message) {
        sendTitle(player, "§aУспіх!", message, 10, 70, 20);
    }

    /**
     * відображає тайтл помилки
     *
     * @param player гравець
     * @param message повідомлення
     */
    public void sendErrorTitle(Player player, String message) {
        sendTitle(player, "§cПомилка!", message, 10, 70, 20);
    }
}