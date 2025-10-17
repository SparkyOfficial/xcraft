package io.github.sparky983.xcraft.core;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * основний клас ядра xcraft
 *
 * @author Андрій Будильников
 */
public class XCraftCore extends JavaPlugin {

    private static XCraftCore instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("xcraft-core завантажено!");
    }

    @Override
    public void onDisable() {
        getLogger().info("xcraft-core вимкнено!");
    }

    public static XCraftCore getInstance() {
        return instance;
    }
}