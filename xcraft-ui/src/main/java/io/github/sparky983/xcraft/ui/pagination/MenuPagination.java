package io.github.sparky983.xcraft.ui.pagination;

import io.github.sparky983.xcraft.items.ItemBuilder;
import io.github.sparky983.xcraft.ui.gui.GuiBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * система пагінації меню для створення багатосторінкових інтерфейсів
 *
 * @author Андрій Будильников
 */
public class MenuPagination {

    private final Pagination pagination;
    private final String menuTitle;
    private final int rows;
    private final BiConsumer<GuiBuilder, Player> pageDecorator;

    public MenuPagination(List<ItemStack> items, int itemsPerPage, String menuTitle, int rows) {
        this(items, itemsPerPage, menuTitle, rows, null);
    }

    public MenuPagination(List<ItemStack> items, int itemsPerPage, String menuTitle, int rows, 
                         BiConsumer<GuiBuilder, Player> pageDecorator) {
        this.pagination = new Pagination(items, itemsPerPage);
        this.menuTitle = menuTitle;
        this.rows = rows;
        this.pageDecorator = pageDecorator;
    }

    /**
     * створює gui для поточної сторінки
     *
     * @param player гравець
     * @return gui
     */
    public GuiBuilder createCurrentPageGui(Player player) {
        String title = menuTitle + " (" + (pagination.getCurrentPage() + 1) + "/" + pagination.getTotalPages() + ")";
        GuiBuilder gui = GuiBuilder.create(title, rows);
        
        // додаємо предмети з поточної сторінки
        List<ItemStack> pageItems = pagination.getCurrentPageItems();
        for (int i = 0; i < pageItems.size(); i++) {
            gui.item(i, pageItems.get(i));
        }
        
        // додаємо кнопки навігації
        if (pagination.hasPreviousPage()) {
            gui.item((rows * 9) - 9, createNavigationItem(Material.ARROW, "§aПопередня сторінка"));
            gui.onClick((rows * 9) - 9, event -> {
                pagination.previousPage();
                createCurrentPageGui(player).open(player);
            });
        }
        
        if (pagination.hasNextPage()) {
            gui.item((rows * 9) - 1, createNavigationItem(Material.ARROW, "§aНаступна сторінка"));
            gui.onClick((rows * 9) - 1, event -> {
                pagination.nextPage();
                createCurrentPageGui(player).open(player);
            });
        }
        
        // застосовуємо декоратор сторінки якщо він є
        if (pageDecorator != null) {
            pageDecorator.accept(gui, player);
        }
        
        return gui;
    }

    /**
     * створює предмет навігації
     *
     * @param material матеріал
     * @param name назва
     * @return предмет
     */
    private ItemStack createNavigationItem(Material material, String name) {
        return ItemBuilder.of(material)
                .name(name)
                .build();
    }

    /**
     * отримує внутрішню пагінацію
     *
     * @return пагінація
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * встановлює поточну сторінку
     *
     * @param page номер сторінки
     */
    public void setCurrentPage(int page) {
        pagination.setCurrentPage(page);
    }

    /**
     * отримує поточну сторінку
     *
     * @return номер сторінки
     */
    public int getCurrentPage() {
        return pagination.getCurrentPage();
    }
}