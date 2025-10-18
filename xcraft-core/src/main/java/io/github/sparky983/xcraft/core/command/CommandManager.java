package io.github.sparky983.xcraft.core.command;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * менеджер команд для простого реєстрування команд
 *
 * @author Андрій Будильников
 */
public class CommandManager implements CommandExecutor {

    private final JavaPlugin plugin;
    private final Map<String, BiConsumer<CommandSender, String[]>> commandHandlers;

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.commandHandlers = new HashMap<>();
    }

    /**
     * реєструє обробник команди
     *
     * @param commandName назва команди
     * @param handler обробник команди
     */
    public void registerCommand(String commandName, BiConsumer<CommandSender, String[]> handler) {
        if (commandName == null || handler == null || plugin == null) {
            return;
        }
        
        commandHandlers.put(commandName.toLowerCase(), handler);
        try {
            PluginCommand command = (PluginCommand) plugin.getCommand(commandName);
            if (command != null) {
                command.setExecutor(this);
            }
        } catch (Exception e) {
            // ігноруємо помилки реєстрації команди
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command == null) {
            return false;
        }
        
        String commandName = command.getName().toLowerCase();
        BiConsumer<CommandSender, String[]> handler = commandHandlers.get(commandName);
        
        if (handler != null) {
            try {
                handler.accept(sender, args);
                return true;
            } catch (Exception e) {
                if (sender != null) {
                    sender.sendMessage("§cсталася помилка при виконанні команди");
                }
                e.printStackTrace();
                return true;
            }
        }
        
        return false;
    }
}