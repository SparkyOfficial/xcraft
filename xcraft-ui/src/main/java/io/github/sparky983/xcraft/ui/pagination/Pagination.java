package io.github.sparky983.xcraft.ui.pagination;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * система пагінації для створення багатосторінкових інтерфейсів
 *
 * @author Андрій Будильников
 */
public class Pagination {

    private final List<ItemStack> items;
    private final int itemsPerPage;
    private int currentPage;

    public Pagination(List<ItemStack> items, int itemsPerPage) {
        this.items = items;
        this.itemsPerPage = itemsPerPage;
        this.currentPage = 0;
    }

    /**
     * отримує предмети для поточної сторінки
     *
     * @return список предметів
     */
    public List<ItemStack> getCurrentPageItems() {
        int start = currentPage * itemsPerPage;
        int end = Math.min(start + itemsPerPage, items.size());
        
        if (start >= items.size()) {
            return List.of(); // повертаємо порожній список
        }
        
        return items.subList(start, end);
    }

    /**
     * переходи на наступну сторінку
     *
     * @return true якщо перехід виконано
     */
    public boolean nextPage() {
        if (currentPage < getTotalPages() - 1) {
            currentPage++;
            return true;
        }
        return false;
    }

    /**
     * переходи на попередню сторінку
     *
     * @return true якщо перехід виконано
     */
    public boolean previousPage() {
        if (currentPage > 0) {
            currentPage--;
            return true;
        }
        return false;
    }

    /**
     * отримує загальну кількість сторінок
     *
     * @return кількість сторінок
     */
    public int getTotalPages() {
        if (items.isEmpty()) {
            return 1;
        }
        return (int) Math.ceil((double) items.size() / itemsPerPage);
    }

    /**
     * отримує поточну сторінку (починаючи з 0)
     *
     * @return номер сторінки
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * встановлює поточну сторінку
     *
     * @param page номер сторінки
     */
    public void setCurrentPage(int page) {
        if (page >= 0 && page < getTotalPages()) {
            this.currentPage = page;
        }
    }

    /**
     * перевіряє чи є наступна сторінка
     *
     * @return true якщо є наступна сторінка
     */
    public boolean hasNextPage() {
        return currentPage < getTotalPages() - 1;
    }

    /**
     * перевіряє чи є попередня сторінка
     *
     * @return true якщо є попередня сторінка
     */
    public boolean hasPreviousPage() {
        return currentPage > 0;
    }

    /**
     * отримує загальну кількість предметів
     *
     * @return кількість предметів
     */
    public int getTotalItems() {
        return items.size();
    }
}