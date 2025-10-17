package io.github.sparky983.xcraft.ui.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * менеджер графічних інтерфейсів для відстеження відкритих gui
 *
 * @author Андрій Будильников
 */
public class GuiManager implements Listener {
    
    private static GuiManager instance;
    private static JavaPlugin plugin;
    
    // зберігає відкриті gui для кожного гравця
    private final Map<UUID, GuiBuilder> openGuis = new ConcurrentHashMap<>();
    
    private GuiManager() {
        // реєструємо listener
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    /**
     * ініціалізує менеджер gui
     *
     * @param plugin плагін
     */
    public static void init(JavaPlugin plugin) {
        if (instance == null) {
            GuiManager.plugin = plugin;
            instance = new GuiManager();
        }
    }
    
    /**
     * отримує екземпляр менеджера
     *
     * @return екземпляр менеджера
     */
    public static GuiManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("gui менеджер не ініціалізовано");
        }
        return instance;
    }
    
    /**
     * реєструє відкрите gui для гравця
     *
     * @param player гравець
     * @param gui gui
     */
    public void registerGui(Player player, GuiBuilder gui) {
        openGuis.put(player.getUniqueId(), gui);
    }
    
    /**
     * видаляє зареєстроване gui для гравця
     *
     * @param player гравець
     */
    public void unregisterGui(Player player) {
        openGuis.remove(player.getUniqueId());
    }
    
    /**
     * отримує відкрите gui для гравця
     *
     * @param player гравець
     * @return gui або null
     */
    public GuiBuilder getOpenGui(Player player) {
        return openGuis.get(player.getUniqueId());
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        GuiBuilder gui = getOpenGui(player);
        
        if (gui != null) {
            // скасовуємо подію якщо gui заблоковано
            if (gui.isLocked()) {
                event.setCancelled(true);
            }
            
            // викликаємо обробники кліків
            int slot = event.getRawSlot();
            if (gui.getClickHandlers().containsKey(slot)) {
                gui.getClickHandlers().get(slot).accept(event);
            }
        }
    }
    
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        GuiBuilder gui = getOpenGui(player);
        
        if (gui != null) {
            // викликаємо обробник закриття якщо він є
            // тепер ми можемо отримати обробник закриття з guibuilder
            Consumer<InventoryCloseEvent> closeHandler = gui.getCloseHandler();
            if (closeHandler != null) {
                closeHandler.accept(event);
            }
            unregisterGui(player);
        }
    }
}