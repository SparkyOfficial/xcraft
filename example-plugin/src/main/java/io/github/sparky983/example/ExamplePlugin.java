package io.github.sparky983.example;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.sparky983.xcraft.core.schedule.XSchedule;
import io.github.sparky983.xcraft.items.ItemBuilder;
import io.github.sparky983.xcraft.items.ItemSerializer;
import io.github.sparky983.xcraft.items.NBTWrapper;
import io.github.sparky983.xcraft.ui.gui.GuiBuilder;

/**
 * приклад використання бібліотеки xcraft
 *
 * @author Андрій Будильников
 */
public class ExamplePlugin extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        // ініціалізуємо модулі xcraft
        GuiBuilder.init(this);
        
        // реєструємо команду для тестування
        this.getCommand("xctest").setExecutor(this);
        
        getLogger().info("приклад xcraft завантажено!");
    }

    @Override
    public void onDisable() {
        getLogger().info("приклад xcraft вимкнено!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("ця команда тільки для гравців!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("використання: /xctest <item|gui|schedule>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "item":
                demonstrateItemFeatures(player);
                break;
            case "gui":
                demonstrateGuiFeatures(player);
                break;
            case "schedule":
                demonstrateScheduleFeatures(player);
                break;
            default:
                player.sendMessage("невідома команда. використовуйте: item, gui, schedule");
        }

        return true;
    }

    private void demonstrateItemFeatures(Player player) {
        // створюємо предмет за допомогою itembuilder
        ItemStack sword = ItemBuilder.of(Material.DIAMOND_SWORD)
                .name("§bЛегендарний Клинок")
                .lore("§7Урон: §c+10", "§7Мана: §9-5")
                .enchant(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 5)
                .unbreakable()
                .flags(org.bukkit.inventory.ItemFlag.HIDE_ATTRIBUTES, org.bukkit.inventory.ItemFlag.HIDE_UNBREAKABLE)
                .build();

        // додаємо nbt теги
        NBTWrapper nbt = new NBTWrapper(sword, this);
        nbt.setString("custom_id", "legendary_fire_sword");
        nbt.setInteger("fire_damage", 12);

        // серіалізуємо предмет
        String serialized = ItemSerializer.toBase64(sword);
        player.sendMessage("предмет серіалізовано: " + serialized);

        // десеріалізуємо предмет
        ItemStack deserialized = ItemSerializer.fromBase64(serialized);
        player.getInventory().addItem(deserialized);
        player.sendMessage("предмет додано до інвентарю!");
    }

    private void demonstrateGuiFeatures(Player player) {
        // створюємо gui за допомогою guibuilder
        GuiBuilder.create("§8Меню навігації", 3)
                .item(11, ItemBuilder.of(Material.COMPASS).name("§aТелепорт на спавн").build())
                .onClick(11, event -> {
                    // телепортуємо гравця на спавн
                    player.teleport(player.getWorld().getSpawnLocation());
                    player.sendMessage("ви телепортовані на спавн!");
                    player.closeInventory();
                })
                .item(13, ItemBuilder.of(Material.CHEST).name("§eВідкрити скриню").build())
                .onClick(13, event -> {
                    player.sendMessage("ви натиснули на скриню!");
                })
                .item(15, ItemBuilder.of(Material.BARRIER).name("§cЗакрити").build())
                .onClick(15, event -> {
                    player.closeInventory();
                })
                .lock() // блокуємо інвентар від виходу предметів
                .open(player);
    }

    private void demonstrateScheduleFeatures(Player player) {
        // використовуємо xschedule для створення таймера
        XSchedule.of(this)
                .timer(20, 20, () -> {
                    player.sendMessage("тик-так!");
                })
                .cancelAfter(10); // скасовуємо після 10 виконань

        player.sendMessage("таймер запущено!");
    }
}