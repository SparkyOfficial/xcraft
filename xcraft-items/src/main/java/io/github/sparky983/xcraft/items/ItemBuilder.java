package io.github.sparky983.xcraft.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * будівельник предметів з підтримкою fluent api
 *
 * @author Андрій Будильников
 */
public class ItemBuilder {

    private final ItemStack itemStack;

    private ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    /**
     * створює новий будівельник предметів
     *
     * @param material матеріал предмету
     * @return новий екземпляр itembuilder
     */
    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material);
    }

    /**
     * встановлює назву предмету
     *
     * @param name назва
     * @return екземпляр itembuilder для подальшого ланцюжка викликів
     */
    public ItemBuilder name(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    /**
     * встановлює опис предмету
     *
     * @param lore опис
     * @return екземпляр itembuilder для подальшого ланцюжка викликів
     */
    public ItemBuilder lore(String... lore) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setLore(Arrays.asList(lore));
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    /**
     * додає рядки до опису предмету
     *
     * @param lore рядки опису
     * @return екземпляр itembuilder для подальшого ланцюжка викликів
     */
    public ItemBuilder addLore(String... lore) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            List<String> currentLore = meta.getLore();
            if (currentLore == null) {
                currentLore = new ArrayList<>();
            }
            currentLore.addAll(Arrays.asList(lore));
            meta.setLore(currentLore);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    /**
     * зачаровує предмет
     *
     * @param enchantment зачарування
     * @param level рівень зачарування
     * @return екземпляр itembuilder для подальшого ланцюжка викликів
     */
    public ItemBuilder enchant(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * робить предмет незламним
     *
     * @return екземпляр itembuilder для подальшого ланцюжка викликів
     */
    public ItemBuilder unbreakable() {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setUnbreakable(true);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    /**
     * додає прапорці до предмету
     *
     * @param flags прапорці
     * @return екземпляр itembuilder для подальшого ланцюжка викликів
     */
    public ItemBuilder flags(ItemFlag... flags) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.addItemFlags(flags);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    /**
     * додає nbt-теги до предмету
     *
     * @param nbtConsumer обробник nbt-тегів
     * @return екземпляр itembuilder для подальшого ланцюжка викликів
     */
    public ItemBuilder nbt(Consumer<NBTWrapper> nbtConsumer) {
        nbtConsumer.accept(new NBTWrapper(itemStack));
        return this;
    }

    /**
     * встановлює кількість предметів
     *
     * @param amount кількість
     * @return екземпляр itembuilder для подальшого ланцюжка викликів
     */
    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    /**
     * будує предмет
     *
     * @return готовий itemstack
     */
    public ItemStack build() {
        return itemStack.clone();
    }
}