package io.github.sparky983.xcraft.core.permission;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

/**
 * менеджер дозволів для простого управління правами гравців
 *
 * @author Андрій Будильников
 */
public class PermissionManager {

    private final Set<String> registeredPermissions;
    private final Map<Player, PermissionAttachment> playerPermissions;

    public PermissionManager() {
        this.registeredPermissions = new HashSet<>();
        this.playerPermissions = new HashMap<>();
    }

    /**
     * реєструє новий дозвіл
     *
     * @param permission дозвіл
     */
    public void registerPermission(String permission) {
        registeredPermissions.add(permission);
    }

    /**
     * перевіряє чи має гравець певний дозвіл
     *
     * @param player гравець
     * @param permission дозвіл
     * @return true якщо гравець має дозвіл
     */
    public boolean hasPermission(Player player, String permission) {
        if (player == null || permission == null) {
            return false;
        }
        return player.hasPermission(permission);
    }

    /**
     * надає гравцю дозвіл
     *
     * @param player гравець
     * @param permission дозвіл
     */
    public void givePermission(Player player, String permission) {
        if (player == null || permission == null) {
            return;
        }
        
        // в реальній реалізації працюємо з permission attachments
        Plugin plugin = Bukkit.getPluginManager().getPlugin("xcraft-core");
        if (plugin == null) {
            // якщо плагін не знайдено, просто реєструємо дозвіл
            registerPermission(permission);
            return;
        }
        
        PermissionAttachment attachment = playerPermissions.computeIfAbsent(player, p -> p.addAttachment(plugin));
        
        if (attachment != null) {
            attachment.setPermission(permission, true);
        }
        
        registerPermission(permission);
    }

    /**
     * забирає у гравця дозвіл
     *
     * @param player гравець
     * @param permission дозвіл
     */
    public void removePermission(Player player, String permission) {
        if (player == null || permission == null) {
            return;
        }
        
        // в реальній реалізації працюємо з permission attachments
        PermissionAttachment attachment = playerPermissions.get(player);
        
        if (attachment != null) {
            attachment.unsetPermission(permission);
        }
        
        registeredPermissions.remove(permission);
    }

    /**
     * отримує всі зареєстровані дозволи
     *
     * @return набір дозволів
     */
    public Set<String> getRegisteredPermissions() {
        return new HashSet<>(registeredPermissions);
    }
    
    /**
     * очищує всі дозволи для гравця
     *
     * @param player гравець
     */
    public void clearPlayerPermissions(Player player) {
        if (player == null) {
            return;
        }
        
        PermissionAttachment attachment = playerPermissions.remove(player);
        
        if (attachment != null) {
            attachment.remove();
        }
    }
    
    /**
     * оновлює дозволи для гравця
     *
     * @param player гравець
     */
    public void updatePermissions(Player player) {
        if (player == null) {
            return;
        }
        player.recalculatePermissions();
    }
}