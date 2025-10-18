package io.github.sparky983.xcraft.core.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * покращений конфігураційний менеджер для роботи з файлами конфігурації
 *
 * @author Андрій Будильников
 */
public class XCraftConfig {

    private final JavaPlugin plugin;
    private final String fileName;
    private FileConfiguration config;
    private File configFile;

    public XCraftConfig(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName != null ? fileName : "config.yml";
        
        if (plugin != null) {
            this.configFile = new File(plugin.getDataFolder(), this.fileName);
            this.config = plugin.getConfig();
            
            // створюємо папку плагіна якщо її немає
            if (!plugin.getDataFolder().exists()) {
                boolean created = plugin.getDataFolder().mkdirs();
                if (!created) {
                    plugin.getLogger().warning("не вдалося створити папку для конфігурації");
                }
            }
        }
        
        reloadConfig();
    }

    /**
     * перезавантажує конфігурацію з файлу
     */
    public void reloadConfig() {
        if (plugin == null || configFile == null) {
            return;
        }
        
        if (configFile.exists()) {
            config = plugin.getConfig();
            plugin.reloadConfig();
        } else {
            // якщо файл не існує, копіюємо з ресурсів
            saveDefaultConfig();
            config = plugin.getConfig();
        }
    }

    /**
     * зберігає конфігурацію в файл
     */
    public void saveConfig() {
        if (plugin == null || configFile == null) {
            return;
        }
        
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("не вдалося зберегти конфігурацію " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * зберігає стандартну конфігурацію з ресурсів
     */
    public void saveDefaultConfig() {
        if (plugin == null || configFile == null) {
            return;
        }
        
        if (!configFile.exists()) {
            plugin.saveResource(fileName, false);
        }
    }

    /**
     * отримує конфігурацію
     *
     * @return конфігурація
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * отримує значення за ключем
     *
     * @param path шлях до значення
     * @param def значення за замовчуванням
     * @param <T> тип значення
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
     * @param path шлях до значення
     * @param value значення
     */
    public void set(String path, Object value) {
        if (config == null || path == null) {
            return;
        }
        config.set(path, value);
    }
}