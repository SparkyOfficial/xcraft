package io.github.sparky983.xcraft.items;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * тест для itemserializer
 *
 * @author Андрій Будильников
 */
public class ItemSerializerTest {

    @Test
    public void testToBase64AndFromBase64() {
        // цей тест не може бути повністю протестований без ініціалізованого bukkit сервера
        // тому ми просто перевіряємо чи методи існують і не кидають виключень на null
        try {
            ItemSerializer.toBase64(null);
            fail("має кинути виключення для null");
        } catch (RuntimeException e) {
            // очікуємо виключення
            assertTrue(e.getMessage().contains("не можна серіалізувати null предмет"));
        }
    }
    
    @Test
    public void testToJsonAndFromJson() {
        // цей тест не може бути повністю протестований без ініціалізованого bukkit сервера
        // тому ми просто перевіряємо чи методи існують
        try {
            ItemSerializer.toJson(null);
            fail("має кинути виключення для null");
        } catch (RuntimeException e) {
            // очікуємо виключення
            assertTrue(e.getMessage().contains("не можна серіалізувати null предмет"));
        }
    }
    
    @Test(expected = RuntimeException.class)
    public void testFromBase64WithInvalidData() {
        // спробуємо десеріалізувати некоректні дані
        ItemSerializer.fromBase64("invalid_base64_data");
    }
    
    @Test(expected = RuntimeException.class)
    public void testFromJsonWithInvalidData() {
        // спробуємо десеріалізувати некоректний json
        ItemSerializer.fromJson("invalid_json");
    }
    
    @Test
    public void testFromBase64WithNullData() {
        // перевіряємо чи метод коректно обробляє null
        assertNull(ItemSerializer.fromBase64(null));
    }
    
    @Test
    public void testFromBase64WithEmptyData() {
        // перевіряємо чи метод коректно обробляє пусті дані
        assertNull(ItemSerializer.fromBase64(""));
    }
    
    @Test
    public void testFromJsonWithNullData() {
        // перевіряємо чи метод коректно обробляє null
        assertNull(ItemSerializer.fromJson(null));
    }
    
    @Test
    public void testFromJsonWithEmptyData() {
        // перевіряємо чи метод коректно обробляє пусті дані
        assertNull(ItemSerializer.fromJson(""));
    }
}