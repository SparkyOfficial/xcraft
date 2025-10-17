package io.github.sparky983.xcraft.core.sound;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * менеджер звуків для простого відтворення звуків
 *
 * @author Андрій Будильников
 */
public class SoundManager {

    /**
     * відтворює звук для гравця
     *
     * @param player гравець
     * @param sound звук
     * @param volume гучність
     * @param pitch тон
     */
    public void playSound(Player player, Sound sound, float volume, float pitch) {
        if (player == null || sound == null) {
            return;
        }
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    /**
     * відтворює звук у певній локації
     *
     * @param location локація
     * @param sound звук
     * @param volume гучність
     * @param pitch тон
     */
    public void playSound(Location location, Sound sound, float volume, float pitch) {
        if (location == null || sound == null) {
            return;
        }
        location.getWorld().playSound(location, sound, volume, pitch);
    }

    /**
     * відтворює звук для гравця з стандартними параметрами
     *
     * @param player гравець
     * @param sound звук
     */
    public void playSound(Player player, Sound sound) {
        playSound(player, sound, 1.0f, 1.0f);
    }

    /**
     * відтворює звук у певній локації з стандартними параметрами
     *
     * @param location локація
     * @param sound звук
     */
    public void playSound(Location location, Sound sound) {
        playSound(location, sound, 1.0f, 1.0f);
    }

    /**
     * відтворює успішний звук для гравця
     *
     * @param player гравець
     */
    public void playSuccessSound(Player player) {
        if (player == null) {
            return;
        }
        playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.5f);
    }

    /**
     * відтворює звук помилки для гравця
     *
     * @param player гравець
     */
    public void playErrorSound(Player player) {
        if (player == null) {
            return;
        }
        playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0f, 0.5f);
    }

    /**
     * відтворює звук попередження для гравця
     *
     * @param player гравець
     */
    public void playWarningSound(Player player) {
        if (player == null) {
            return;
        }
        playSound(player, Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 0.5f);
    }
}