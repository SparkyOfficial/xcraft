package io.github.sparky983.xcraft.items;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * утиліта для серіалізації предметів
 *
 * @author Андрій Будильников
 */
public class ItemSerializer {

    private static final Gson GSON = new Gson();

    /**
     * серіалізує предмет в строку base64
     *
     * @param itemStack предмет для серіалізації
     * @return серіалізований предмет у вигляді строки base64
     */
    public static String toBase64(ItemStack itemStack) {
        // перевіряємо чи предмет не null
        if (itemStack == null) {
            throw new RuntimeException("не можна серіалізувати null предмет");
        }
        
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(itemStack);
            dataOutput.close();
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("не вдалося серіалізувати предмет", e);
        }
    }

    /**
     * десеріалізує предмет з строки base64
     *
     * @param data серіалізований предмет у вигляді строки base64
     * @return десеріалізований предмет
     */
    public static ItemStack fromBase64(String data) {
        // перевіряємо чи дані не null або пусті
        if (data == null || data.isEmpty()) {
            return null;
        }
        
        try {
            byte[] decodedData = Base64.getDecoder().decode(data);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedData);
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack itemStack = (ItemStack) dataInput.readObject();
            dataInput.close();
            return itemStack;
        } catch (Exception e) {
            throw new RuntimeException("не вдалося десеріалізувати предмет", e);
        }
    }

    /**
     * серіалізує предмет в json 
     *
     * @param itemStack предмет для серіалізації
     * @return серіалізований предмет у вигляді json
     */
    public static String toJson(ItemStack itemStack) {
        // перевіряємо чи предмет не null
        if (itemStack == null) {
            throw new RuntimeException("не можна серіалізувати null предмет");
        }
        
        // серіалізуємо в base64 і обгортаємо в json
        String base64 = toBase64(itemStack);
        JsonObject json = new JsonObject();
        json.addProperty("item", base64);
        return GSON.toJson(json);
    }

    /**
     * десеріалізує предмет з json
     *
     * @param json серіалізований предмет у вигляді json
     * @return десеріалізований предмет
     */
    public static ItemStack fromJson(String json) {
        // перевіряємо чи json не null або пустий
        if (json == null || json.isEmpty()) {
            return null;
        }
        
        try {
            JsonObject jsonObject = GSON.fromJson(json, JsonObject.class);
            String base64 = jsonObject.get("item").getAsString();
            return fromBase64(base64);
        } catch (Exception e) {
            throw new RuntimeException("не вдалося десеріалізувати предмет з json", e);
        }
    }
}