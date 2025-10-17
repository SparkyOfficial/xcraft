package io.github.sparky983.xcraft.items;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * обгортка для роботи з nbt-тегами
 *
 * @author Андрій Будильников
 */
public class NBTWrapper {

    private final ItemStack itemStack;
    private final JavaPlugin plugin; // для створення namespacedkey

    public NBTWrapper(ItemStack itemStack) {
        this.itemStack = itemStack;
        // для зворотної сумісності, але краще використовувати конструктор з плагіном
        this.plugin = null;
    }

    /**
     * встановлює строкове значення nbt-тегу
     *
     * @param key ключ
     * @param value значення
     */
    public void setString(String key, String value) {
        if (itemStack == null || itemStack.getItemMeta() == null) {
            return;
        }
        
        // перевіряємо чи передали плагін
        if (plugin == null) {
            throw new IllegalStateException("потрібно використовувати конструктор з плагіном для роботи з nbt");
        }
        
        ItemMeta meta = itemStack.getItemMeta();
        // використовуємо persistent data container замість прямого доступу до nbt
        // бо прямий доступ до nbt вимагає nms (net.minecraft.server) що порушує версійну незалежність
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(plugin, key), PersistentDataType.STRING, value);
        itemStack.setItemMeta(meta);
    }

    /**
     * встановлює цілочисельне значення nbt-тегу
     *
     * @param key ключ
     * @param value значення
     */
    public void setInteger(String key, int value) {
        if (itemStack == null || itemStack.getItemMeta() == null) {
            return;
        }
        
        // перевіряємо чи передали плагін
        if (plugin == null) {
            throw new IllegalStateException("потрібно використовувати конструктор з плагіном для роботи з nbt");
        }
        
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(plugin, key), PersistentDataType.INTEGER, value);
        itemStack.setItemMeta(meta);
    }

    /**
     * отримує строкове значення nbt-тегу
     *
     * @param key ключ
     * @return значення або null
     */
    public String getString(String key) {
        if (itemStack == null || itemStack.getItemMeta() == null) {
            return null;
        }
        
        // перевіряємо чи передали плагін
        if (plugin == null) {
            throw new IllegalStateException("потрібно використовувати конструктор з плагіном для роботи з nbt");
        }
        
        PersistentDataContainer container = itemStack.getItemMeta().getPersistentDataContainer();
        return container.get(new NamespacedKey(plugin, key), PersistentDataType.STRING);
    }

    /**
     * отримує цілочисельне значення nbt-тегу
     *
     * @param key ключ
     * @return значення або 0
     */
    public int getInteger(String key) {
        if (itemStack == null || itemStack.getItemMeta() == null) {
            return 0;
        }
        
        // перевіряємо чи передали плагін
        if (plugin == null) {
            throw new IllegalStateException("потрібно використовувати конструктор з плагіном для роботи з nbt");
        }
        
        PersistentDataContainer container = itemStack.getItemMeta().getPersistentDataContainer();
        Integer value = container.get(new NamespacedKey(plugin, key), PersistentDataType.INTEGER);
        return value != null ? value : 0;
    }
    
    /**
     * конструктор з плагіном для створення namespacedkey
     *
     * @param itemStack предмет
     * @param plugin плагін
     */
    public NBTWrapper(ItemStack itemStack, JavaPlugin plugin) {
        this.itemStack = itemStack;
        this.plugin = plugin;
    }
}