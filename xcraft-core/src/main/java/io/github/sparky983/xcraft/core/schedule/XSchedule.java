package io.github.sparky983.xcraft.core.schedule;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

/**
 * планувальник задач з підтримкою fluent api
 *
 * @author Андрій Будильников
 */
public class XSchedule {

    private final JavaPlugin plugin;
    private BukkitTask task;
    private int executionCount = 0;
    private int maxExecutions = -1; // -1 означає нескінченну кількість виконань

    private XSchedule(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * створює новий планувальник задач
     *
     * @param plugin плагін, від імені якого буде виконуватись задача
     * @return новий екземпляр xschedule
     */
    public static XSchedule of(JavaPlugin plugin) {
        return new XSchedule(plugin);
    }

    /**
     * створює задачу, яка виконується з певним інтервалом
     *
     * @param delay    затримка перед першим виконанням (в тіках)
     * @param interval інтервал між виконаннями (в тіках)
     * @param task     задача для виконання
     * @return екземпляр xschedule для подальшого ланцюжка викликів
     */
    public XSchedule timer(int delay, int interval, Runnable task) {
        this.task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            task.run();
            executionCount++;

            // якщо встановлено максимальну кількість виконань і вона досягнута, скасовуємо задачу
            if (maxExecutions > 0 && executionCount >= maxExecutions) {
                cancel();
            }
        }, delay, interval);

        return this;
    }

    /**
     * створює задачу, яка виконується один раз
     *
     * @param delay затримка перед виконанням (в тіках)
     * @param task  задача для виконання
     * @return екземпляр xschedule для подальшого ланцюжка викликів
     */
    public XSchedule delay(int delay, Runnable task) {
        this.task = Bukkit.getScheduler().runTaskLater(plugin, task, delay);
        return this;
    }

    /**
     * скасовує задачу після певної кількості виконань
     *
     * @param executions кількість виконань після яких задача буде скасована
     * @return екземпляр xschedule для подальшого ланцюжка викликів
     */
    public XSchedule cancelAfter(int executions) {
        this.maxExecutions = executions;
        return this;
    }

    /**
     * скасовує задачу
     */
    public void cancel() {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
    }
}