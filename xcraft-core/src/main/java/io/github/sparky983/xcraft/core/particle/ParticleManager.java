package io.github.sparky983.xcraft.core.particle;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

/**
 * менеджер частинок для створення ефектів
 *
 * @author Андрій Будильников
 */
public class ParticleManager {

    /**
     * відображає частинки для гравця
     *
     * @param player гравець
     * @param particle тип частинок
     * @param location локація
     * @param count кількість частинок
     */
    public void spawnParticle(Player player, Particle particle, Location location, int count) {
        if (player == null || particle == null || location == null) {
            return;
        }
        player.spawnParticle(particle, location, count);
    }

    /**
     * відображає частинки у певній локації
     *
     * @param particle тип частинок
     * @param location локація
     * @param count кількість частинок
     */
    public void spawnParticle(Particle particle, Location location, int count) {
        if (particle == null || location == null || location.getWorld() == null) {
            return;
        }
        location.getWorld().spawnParticle(particle, location, count);
    }

    /**
     * відображає частинки для гравця з параметрами
     *
     * @param player гравець
     * @param particle тип частинок
     * @param location локація
     * @param count кількість частинок
     * @param offsetX зміщення по X
     * @param offsetY зміщення по Y
     * @param offsetZ зміщення по Z
     */
    public void spawnParticle(Player player, Particle particle, Location location, int count, 
                             double offsetX, double offsetY, double offsetZ) {
        if (player == null || particle == null || location == null) {
            return;
        }
        player.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ);
    }

    /**
     * відображає частинки у певній локації з параметрами
     *
     * @param particle тип частинок
     * @param location локація
     * @param count кількість частинок
     * @param offsetX зміщення по X
     * @param offsetY зміщення по Y
     * @param offsetZ зміщення по Z
     */
    public void spawnParticle(Particle particle, Location location, int count, 
                             double offsetX, double offsetY, double offsetZ) {
        if (particle == null || location == null || location.getWorld() == null) {
            return;
        }
        location.getWorld().spawnParticle(particle, location, count, offsetX, offsetY, offsetZ);
    }

    /**
     * відображає ефект вибуху для гравця
     *
     * @param player гравець
     * @param location локація
     */
    public void spawnExplosionEffect(Player player, Location location) {
        if (player == null || location == null) {
            return;
        }
        spawnParticle(player, Particle.EXPLOSION_NORMAL, location, 10, 0.5, 0.5, 0.5);
    }

    /**
     * відображає ефект вибуху у певній локації
     *
     * @param location локація
     */
    public void spawnExplosionEffect(Location location) {
        if (location == null) {
            return;
        }
        spawnParticle(Particle.EXPLOSION_NORMAL, location, 10, 0.5, 0.5, 0.5);
    }

    /**
     * відображає ефект магії для гравця
     *
     * @param player гравець
     * @param location локація
     */
    public void spawnMagicEffect(Player player, Location location) {
        if (player == null || location == null) {
            return;
        }
        spawnParticle(player, Particle.SPELL_WITCH, location, 15, 0.5, 0.5, 0.5);
    }

    /**
     * відображає ефект магії у певній локації
     *
     * @param location локація
     */
    public void spawnMagicEffect(Location location) {
        if (location == null) {
            return;
        }
        spawnParticle(Particle.SPELL_WITCH, location, 15, 0.5, 0.5, 0.5);
    }
}