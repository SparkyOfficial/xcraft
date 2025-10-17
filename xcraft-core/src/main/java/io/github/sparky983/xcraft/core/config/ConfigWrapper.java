package io.github.sparky983.xcraft.core.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

/**
 * обгортка над fileconfiguration для зручної роботи з конфігураціями
 *
 * @author Андрій Будильников
 */
public class ConfigWrapper {

    private final JavaPlugin plugin;
    private final String fileName;
    private FileConfiguration config;

    public ConfigWrapper(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName != null ? fileName : "config.yml";
        this.config = plugin != null ? plugin.getConfig() : null;
        if (plugin != null) {
            plugin.saveDefaultConfig();
        }
    }

    /**
     * отримує значення за ключем
     *
     * @param path шлях до значення
     * @param def  значення за замовчуванням
     * @param <T>  тип значення
     * @return значення або значення за замовчуванням
     */
    public <T> T get(String path, T def) {
        if (config == null || path == null) {
            return def;
        }
        return (T) config.get(path, def);
    }

    /**
     * встановлює значення за ключем
     *
     * @param path  шлях до значення
     * @param value значення
     */
    public void set(String path, Object value) {
        if (config == null || path == null) {
            return;
        }
        config.set(path, value);
    }

    /**
     * зберігає конфігурацію
     */
    public void save() {
        if (plugin == null) {
            return;
        }
        plugin.saveConfig();
    }

    /**
     * перезавантажує конфігурацію
     */
    public void reload() {
        if (plugin == null) {
            return;
        }
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

    /**
     * отримує локацію з конфігурації
     *
     * @param path шлях до локації
     * @return локація або null
     */
    public Location getLocation(String path) {
        if (config == null || path == null) {
            return null;
        }
        
        String serialized = config.getString(path);
        if (serialized == null || serialized.isEmpty()) {
            return null;
        }

        try {
            String[] parts = serialized.split(":");
            if (parts.length >= 4) {
                String worldName = parts[0];
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double z = Double.parseDouble(parts[3]);
                
                // спробуємо отримати світ
                org.bukkit.World world = org.bukkit.Bukkit.getWorld(worldName);
                if (world == null) {
                    return null;
                }
                
                if (parts.length >= 6) {
                    float yaw = Float.parseFloat(parts[4]);
                    float pitch = Float.parseFloat(parts[5]);
                    return new Location(world, x, y, z, yaw, pitch);
                } else {
                    return new Location(world, x, y, z);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * зберігає локацію в конфігурації
     *
     * @param path     шлях до локації
     * @param location локація для збереження
     */
    public void setLocation(String path, Location location) {
        if (config == null || path == null) {
            return;
        }
        
        if (location == null) {
            config.set(path, null);
            return;
        }

        String serialized = location.getWorld().getName() + ":" +
                location.getX() + ":" +
                location.getY() + ":" +
                location.getZ() + ":" +
                location.getYaw() + ":" +
                location.getPitch();
        config.set(path, serialized);
    }

    /**
     * отримує itemstack з конфігурації
     *
     * @param path шлях до itemstack
     * @return itemstack або null
     */
    public ItemStack getItemStack(String path) {
        if (config == null || path == null) {
            return null;
        }
        
        String serialized = config.getString(path);
        if (serialized == null || serialized.isEmpty()) {
            return null;
        }

        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(serialized));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack itemStack = (ItemStack) dataInput.readObject();
            dataInput.close();
            return itemStack;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * зберігає itemstack в конфігурації
     *
     * @param path      шлях до itemstack
     * @param itemStack itemstack для збереження
     */
    public void setItemStack(String path, ItemStack itemStack) {
        if (config == null || path == null) {
            return;
        }
        
        if (itemStack == null) {
            config.set(path, null);
            return;
        }

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(itemStack);
            dataOutput.close();
            config.set(path, Base64Coder.encodeLines(outputStream.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}