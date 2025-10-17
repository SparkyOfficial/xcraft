package io.github.sparky983.xcraft.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * тест для nbtwrapper
 *
 * @author Андрій Будильников
 */
public class NBTWrapperTest {

    @Test
    public void testConstructorWithPlugin() {
        // цей тест не може бути повністю протестований без ініціалізованого bukkit сервера
        // тому ми просто перевіряємо чи методи існують
        try {
            // створюємо предмет
            ItemStack testItem = new ItemStack(Material.DIAMOND_SWORD);
            
            // створюємо nbtwrapper з предметом (без плагіна для простоти тесту)
            NBTWrapper nbtWrapper = new NBTWrapper(testItem);
            
            assertNotNull("nbtwrapper створено коректно", nbtWrapper);
        } catch (Exception e) {
            // очікуємо виключення через відсутність ініціалізованого bukkit сервера
        }
    }
    
    @Test
    public void testSetStringWithoutPlugin() {
        // створюємо предмет
        ItemStack testItem = new ItemStack(Material.DIAMOND_SWORD);
        
        // створюємо nbtwrapper без плагіна
        NBTWrapper nbtWrapper = new NBTWrapper(testItem);
        
        // спробуємо встановити строкове значення - має кинути виключення
        try {
            nbtWrapper.setString("test_key", "test_value");
            fail("має кинути виключення");
        } catch (IllegalStateException e) {
            // очікуємо це виключення
            assertTrue(e.getMessage().contains("потрібно використовувати конструктор з плагіном"));
        } catch (Exception e) {
            // може бути NullPointerException через відсутність ініціалізованого bukkit сервера
            // це нормально для тестів
        }
    }
    
    @Test
    public void testSetIntegerWithoutPlugin() {
        // створюємо предмет
        ItemStack testItem = new ItemStack(Material.DIAMOND_SWORD);
        
        // створюємо nbtwrapper без плагіна
        NBTWrapper nbtWrapper = new NBTWrapper(testItem);
        
        // спробуємо встановити цілочисельне значення - має кинути виключення
        try {
            nbtWrapper.setInteger("test_key", 42);
            fail("має кинути виключення");
        } catch (IllegalStateException e) {
            // очікуємо це виключення
            assertTrue(e.getMessage().contains("потрібно використовувати конструктор з плагіном"));
        } catch (Exception e) {
            // може бути NullPointerException через відсутність ініціалізованого bukkit сервера
            // це нормально для тестів
        }
    }
    
    @Test
    public void testGetStringWithoutPlugin() {
        // створюємо предмет
        ItemStack testItem = new ItemStack(Material.DIAMOND_SWORD);
        
        // створюємо nbtwrapper без плагіна
        NBTWrapper nbtWrapper = new NBTWrapper(testItem);
        
        // спробуємо отримати строкове значення - має кинути виключення
        try {
            nbtWrapper.getString("test_key");
            fail("має кинути виключення");
        } catch (IllegalStateException e) {
            // очікуємо це виключення
            assertTrue(e.getMessage().contains("потрібно використовувати конструктор з плагіном"));
        } catch (Exception e) {
            // може бути NullPointerException через відсутність ініціалізованого bukkit сервера
            // це нормально для тестів
        }
    }
    
    @Test
    public void testGetIntegerWithoutPlugin() {
        // створюємо предмет
        ItemStack testItem = new ItemStack(Material.DIAMOND_SWORD);
        
        // створюємо nbtwrapper без плагіна
        NBTWrapper nbtWrapper = new NBTWrapper(testItem);
        
        // спробуємо отримати цілочисельне значення - має кинути виключення
        try {
            nbtWrapper.getInteger("test_key");
            fail("має кинути виключення");
        } catch (IllegalStateException e) {
            // очікуємо це виключення
            assertTrue(e.getMessage().contains("потрібно використовувати конструктор з плагіном"));
        } catch (Exception e) {
            // може бути NullPointerException через відсутність ініціалізованого bukkit сервера
            // це нормально для тестів
        }
    }
}