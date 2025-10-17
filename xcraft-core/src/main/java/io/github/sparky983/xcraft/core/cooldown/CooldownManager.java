package io.github.sparky983.xcraft.core.cooldown;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

/**
 * менеджер кулдаунів для управління часом очікування
 *
 * @author Андрій Будильников
 */
public class CooldownManager {

    private final Map<String, Map<UUID, Long>> cooldowns;

    public CooldownManager() {
        this.cooldowns = new HashMap<>();
    }

    /**
     * встановлює кулдаун для гравця
     *
     * @param cooldownName назва кулдауну
     * @param player гравець
     * @param duration тривалість в мілісекундах
     */
    public void setCooldown(String cooldownName, Player player, long duration) {
        cooldowns.computeIfAbsent(cooldownName, k -> new HashMap<>());
        cooldowns.get(cooldownName).put(player.getUniqueId(), System.currentTimeMillis() + duration);
    }

    /**
     * перевіряє чи активний кулдаун для гравця
     *
     * @param cooldownName назва кулдауну
     * @param player гравець
     * @return true якщо кулдаун активний
     */
    public boolean hasCooldown(String cooldownName, Player player) {
        Map<UUID, Long> cooldownMap = cooldowns.get(cooldownName);
        if (cooldownMap == null) {
            return false;
        }
        
        Long expireTime = cooldownMap.get(player.getUniqueId());
        if (expireTime == null) {
            return false;
        }
        
        if (System.currentTimeMillis() > expireTime) {
            cooldownMap.remove(player.getUniqueId());
            return false;
        }
        
        return true;
    }

    /**
     * отримує залишок часу кулдауну в мілісекундах
     *
     * @param cooldownName назва кулдауну
     * @param player гравець
     * @return залишок часу або 0
     */
    public long getCooldownTime(String cooldownName, Player player) {
        Map<UUID, Long> cooldownMap = cooldowns.get(cooldownName);
        if (cooldownMap == null) {
            return 0;
        }
        
        Long expireTime = cooldownMap.get(player.getUniqueId());
        if (expireTime == null) {
            return 0;
        }
        
        long remaining = expireTime - System.currentTimeMillis();
        if (remaining <= 0) {
            cooldownMap.remove(player.getUniqueId());
            return 0;
        }
        
        return remaining;
    }

    /**
     * видаляє кулдаун для гравця
     *
     * @param cooldownName назва кулдауну
     * @param player гравець
     */
    public void removeCooldown(String cooldownName, Player player) {
        Map<UUID, Long> cooldownMap = cooldowns.get(cooldownName);
        if (cooldownMap != null) {
            cooldownMap.remove(player.getUniqueId());
        }
    }

    /**
     * очищує всі кулдауни
     */
    public void clearAllCooldowns() {
        cooldowns.clear();
    }
}