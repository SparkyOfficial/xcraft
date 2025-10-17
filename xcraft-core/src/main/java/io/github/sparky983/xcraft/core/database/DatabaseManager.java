package io.github.sparky983.xcraft.core.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * менеджер бази даних для роботи з sqlite
 *
 * @author Андрій Будильников
 */
public class DatabaseManager {

    private final JavaPlugin plugin;
    private Connection connection;

    public DatabaseManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * ініціалізує з'єднання з базою даних
     */
    public void initialize() {
        try {
            // створюємо папку для бази даних якщо її немає
            File dataFolder = new File(plugin.getDataFolder(), "database.db");
            if (!dataFolder.exists()) {
                boolean dirsCreated = plugin.getDataFolder().mkdirs();
                // Можна додати логування результату створення директорій, якщо потрібно
            }

            // завантажуємо драйвер sqlite
            Class.forName("org.sqlite.JDBC");
            
            // створюємо з'єднання
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            
            plugin.getLogger().info("база даних ініціалізована");
        } catch (Exception e) {
            plugin.getLogger().severe("не вдалося ініціалізувати базу даних");
            e.printStackTrace();
        }
    }

    /**
     * отримує з'єднання з базою даних
     *
     * @return з'єднання
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * закриває з'єднання з базою даних
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("не вдалося закрити з'єднання з базою даних");
            e.printStackTrace();
        }
    }

    /**
     * виконує sql запит
     *
     * @param sql sql запит
     */
    public void executeSQL(String sql) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            plugin.getLogger().severe("не вдалося виконати sql запит: " + sql);
            e.printStackTrace();
        }
    }
}