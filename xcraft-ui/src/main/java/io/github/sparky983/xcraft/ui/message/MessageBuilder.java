package io.github.sparky983.xcraft.ui.message;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * будівельник повідомлень з підтримкою клікабельних елементів та підказок
 *
 * @author Андрій Будильников
 */
public class MessageBuilder {

    private final List<BaseComponent> components;

    private MessageBuilder() {
        this.components = new ArrayList<>();
    }

    /**
     * створює новий будівельник повідомлень
     *
     * @return новий екземпляр messagebuilder
     */
    public static MessageBuilder create() {
        return new MessageBuilder();
    }

    /**
     * додає текст до повідомлення
     *
     * @param text текст
     * @return екземпляр messagebuilder для подальшого ланцюжка викликів
     */
    public MessageBuilder text(String text) {
        components.add(new TextComponent(text));
        return this;
    }

    /**
     * додає текст з подією кліку
     *
     * @param text текст
     * @param action дія при кліку
     * @param value значення дії
     * @return екземпляр messagebuilder для подальшого ланцюжка викликів
     */
    public MessageBuilder clickableText(String text, ClickEvent.Action action, String value) {
        TextComponent component = new TextComponent(text);
        component.setClickEvent(new ClickEvent(action, value));
        components.add(component);
        return this;
    }

    /**
     * додає текст з подією наведення
     *
     * @param text текст
     * @param hoverText текст підказки
     * @return екземпляр messagebuilder для подальшого ланцюжка викликів
     */
    public MessageBuilder hoverText(String text, String hoverText) {
        TextComponent component = new TextComponent(text);
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
            new ComponentBuilder(hoverText).create()));
        components.add(component);
        return this;
    }

    /**
     * додає текст з подією кліку та наведення
     *
     * @param text текст
     * @param clickAction дія при кліку
     * @param clickValue значення дії кліку
     * @param hoverText текст підказки
     * @return екземпляр messagebuilder для подальшого ланцюжка викликів
     */
    public MessageBuilder interactiveText(String text, ClickEvent.Action clickAction, 
                                         String clickValue, String hoverText) {
        TextComponent component = new TextComponent(text);
        component.setClickEvent(new ClickEvent(clickAction, clickValue));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
            new ComponentBuilder(hoverText).create()));
        components.add(component);
        return this;
    }

    /**
     * відправляє повідомлення гравцю
     *
     * @param player гравець
     */
    public void sendTo(Player player) {
        player.spigot().sendMessage(components.toArray(new BaseComponent[0]));
    }

    /**
     * перетворює повідомлення в масив компонентів
     *
     * @return масив компонентів
     */
    public BaseComponent[] build() {
        return components.toArray(new BaseComponent[0]);
    }
}