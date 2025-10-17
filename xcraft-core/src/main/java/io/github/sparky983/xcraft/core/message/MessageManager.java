package io.github.sparky983.xcraft.core.message;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

/**
 * менеджер повідомлень для централізованого управління текстами
 *
 * @author Андрій Будильников
 */
public class MessageManager {

    private final Map<String, String> messages;
    private String defaultPrefix = "";

    public MessageManager() {
        this.messages = new HashMap<>();
    }

    /**
     * додає повідомлення
     *
     * @param key ключ
     * @param message повідомлення
     */
    public void addMessage(String key, String message) {
        messages.put(key, message);
    }

    /**
     * отримує повідомлення за ключем
     *
     * @param key ключ
     * @return повідомлення
     */
    public String getMessage(String key) {
        return messages.getOrDefault(key, "§cповідомлення не знайдено: " + key);
    }

    /**
     * відправляє повідомлення гравцю
     *
     * @param player гравець
     * @param key ключ повідомлення
     */
    public void sendMessage(Player player, String key) {
        player.sendMessage(defaultPrefix + getMessage(key));
    }

    /**
     * відправляє повідомлення гравцю з префіксом
     *
     * @param player гравець
     * @param key ключ повідомлення
     * @param prefix префікс
     */
    public void sendMessage(Player player, String key, String prefix) {
        player.sendMessage(prefix + getMessage(key));
    }

    /**
     * встановлює стандартний префікс
     *
     * @param prefix префікс
     */
    public void setDefaultPrefix(String prefix) {
        this.defaultPrefix = prefix;
    }

    /**
     * отримує стандартний префікс
     *
     * @return префікс
     */
    public String getDefaultPrefix() {
        return defaultPrefix;
    }

    /**
     * замінює змінні в повідомленні
     *
     * @param message повідомлення
     * @param replacements заміни
     * @return повідомлення з замінами
     */
    public String formatMessage(String message, Map<String, String> replacements) {
        String formatted = message;
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            formatted = formatted.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return formatted;
    }
}