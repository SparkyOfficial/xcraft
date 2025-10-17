package io.github.sparky983.xcraft.ui.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * будівельник графічного інтерфейсу з підтримкою fluent api
 *
 * @author Андрій Будильников
 */
public class GuiBuilder {

    private final Inventory inventory;
    private final Map<Integer, Consumer<InventoryClickEvent>> clickHandlers;
    private final Map<String, Object> metadata; // для зберігання додаткових даних
    private boolean locked = false;
    private Consumer<InventoryCloseEvent> closeHandler;
    
    private final UUID id;

    private GuiBuilder(String title, int rows) {
        this.inventory = Bukkit.createInventory(null, rows * 9, title);
        this.clickHandlers = new HashMap<>();
        this.metadata = new HashMap<>();
        this.id = UUID.randomUUID();
    }

    /**
     * ініціалізує guibuilder з плагіном (потрібно для реєстрації listener)
     *
     * @param plugin плагін
     */
    public static void init(JavaPlugin plugin) {
        GuiManager.init(plugin);
    }

    /**
     * створює новий будівельник gui
     *
     * @param title назва інвентарю
     * @param rows  кількість рядків (1-6)
     * @return новий екземпляр guibuilder
     */
    public static GuiBuilder create(String title, int rows) {
        return new GuiBuilder(title, rows);
    }

    /**
     * додає предмет до інвентарю
     *
     * @param slot слот для предмету
     * @param item предмет
     * @return екземпляр guibuilder для подальшого ланцюжка викликів
     */
    public GuiBuilder item(int slot, ItemStack item) {
        inventory.setItem(slot, item);
        return this;
    }

    /**
     * додає предмет до інвентарю без визначення слоту (додається до першого вільного)
     *
     * @param item предмет
     * @return екземпляр guibuilder для подальшого ланцюжка викликів
     */
    public GuiBuilder item(ItemStack item) {
        inventory.addItem(item);
        return this;
    }

    /**
     * додає обробник кліку по конкретному слоту
     *
     * @param slot    слот
     * @param handler обробник кліку
     * @return екземпляр guibuilder для подальшого ланцюжка викликів
     */
    public GuiBuilder onClick(int slot, Consumer<InventoryClickEvent> handler) {
        clickHandlers.put(slot, handler);
        return this;
    }

    /**
     * додає обробник закриття інвентарю
     *
     * @param handler обробник закриття
     * @return екземпляр guibuilder для подальшого ланцюжка викликів
     */
    public GuiBuilder onClose(Consumer<InventoryCloseEvent> handler) {
        this.closeHandler = handler;
        return this;
    }

    /**
     * блокує інвентар від виходу предметів
     *
     * @return екземпляр guibuilder для подальшого ланцюжка викликів
     */
    public GuiBuilder lock() {
        this.locked = true;
        return this;
    }

    /**
     * відкриває інвентар для гравця
     *
     * @param player гравець
     */
    public void open(Player player) {
        player.openInventory(inventory);
        // реєструємо gui для гравця
        GuiManager.getInstance().registerGui(player, this);
    }

    /**
     * перевіряє чи заблокований інвентар
     *
     * @return true якщо інвентар заблокований
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * отримує обробники кліків
     *
     * @return мапа обробників кліків
     */
    public Map<Integer, Consumer<InventoryClickEvent>> getClickHandlers() {
        return clickHandlers;
    }

    /**
     * отримує обробник закриття інвентарю
     *
     * @return обробник закриття або null
     */
    public Consumer<InventoryCloseEvent> getCloseHandler() {
        return closeHandler;
    }

    /**
     * додає метадані до gui
     *
     * @param key ключ
     * @param value значення
     * @return екземпляр guibuilder для подальшого ланцюжка викликів
     */
    public GuiBuilder metadata(String key, Object value) {
        metadata.put(key, value);
        return this;
    }

    /**
     * отримує метадані з gui
     *
     * @param key ключ
     * @return значення або null
     */
    public Object getMetadata(String key) {
        return metadata.get(key);
    }

    /**
     * отримує ідентифікатор gui
     *
     * @return ідентифікатор
     */
    public UUID getId() {
        return id;
    }
}