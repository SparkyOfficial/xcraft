# xcraft

[![en](https://img.shields.io/badge/lang-en-red.svg)](README.md)
[![ru](https://img.shields.io/badge/lang-ru-yellow.svg)](README.ru.md)
[![uk](https://img.shields.io/badge/lang-uk-blue.svg)](README.uk.md)

xcraft - це бібліотека для розробки плагінів Minecraft, яка розширює стандартний Bukkit API, роблячи його більш зручним і потужним.

Символ "x" в назві означає "eXtended" (розширений), "eXpressive" (виразний) і "eXperimental" (експериментальний).

## Філософія та ключові принципи

1. **Модульність**: Бібліотека складається з незалежних модулів. Розробник підключає лише те, що йому потрібно.
2. **Fluent API**: Весь API будується на ланцюжках викликів (builder'ах), що робить код читабельним і лаконічним.
3. **Версійна незалежність**: Максимальна абстракція від прямого використання NMS.
4. **Продуктивність**: Використання найшвидших і перевірених рішень для "важких" операцій.
5. **Відкритість**: Бібліотека є Open-Source з хорошою документацією.

## Структура модулів

### xcraft-core
Базовий модуль, від якого залежать всі інші.

- **XSchedule**: Покращений планувальник задач
- **ConfigWrapper**: Обгортка над FileConfiguration
- **Кастомні події**: AsyncPlayerMoveEvent, PlayerJumpEvent, PlayerUseItemEvent

### xcraft-ui
Модуль для створення елементів інтерфейсу.

- **GuiBuilder**: Конструктор GUI-меню
- **MessageBuilder**: Конструктор повідомлень
- **DisplayManager**: Менеджер відображення (BossBar, ActionBar, Title, Scoreboard)

### xcraft-items
Все для роботи з предметами.

- **ItemBuilder**: Створення ItemStack через ланцюжок викликів
- **NBT API**: Версійно-незалежний спосіб роботи з NBT-тегами
- **Серіалізація**: Перетворення ItemStack в строку і назад

### xcraft-world
Для маніпуляцій зі світом і ефектами.

- **WorldEdit wrapper**: Спрощений API для FastAsyncWorldEdit
- **Particle drawer**: API для створення ефектів з частинок
- **Region manager**: Робота з регіонами

### xcraft-network (Опціональний)
Абстракції над низькорівневими речами.

- **Packet wrapper**: Спрощений API для ProtocolLib
- **NPC API**: API для створення неігрових персонажів

## Встановлення

Додайте залежність до вашого `pom.xml`:

```xml
<dependencies>
    <!-- Додайте потрібні модулі xcraft -->
    <dependency>
        <groupId>io.github.sparky983</groupId>
        <artifactId>xcraft-core</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

## Приклади використання

### XSchedule
```java
// Замість громіздкого BukkitRunnable
XSchedule.timer(20, 20, () -> {
    Bukkit.broadcastMessage("Tick-Tock!");
}).cancelAfter(10); // Зупиниться після 10 виконань
```

### GuiBuilder
```java
GuiBuilder.create("§8Меню навігації", 3)
    .item(11, ItemBuilder.of(Material.COMPASS).name("§aТелепорт на спавн").build())
    .onClick(event -> {
        event.getWhoClicked().teleport(spawnLocation);
        event.getWhoClicked().sendMessage("Ви телепортовані на спавн!");
    })
    .lock() // Заборонити брати предмети
    .open(player);
```

### ItemBuilder та NBT API
```java
ItemStack magicSword = ItemBuilder.of(Material.DIAMOND_SWORD)
    .name("§bЛегендарний Клинок")
    .lore("§7Урон: §c+10", "§7Мана: §9-5")
    .enchant(Enchantment.DAMAGE_ALL, 5)
    .unbreakable()
    .flags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
    .nbt(nbt -> {
        nbt.setString("custom_id", "legendary_fire_sword");
        nbt.setInteger("fire_damage", 12);
    })
    .build();
```

### ParticleDrawer
```java
// Намалювати червоне коло під гравцем
ParticleDrawer.circle(player.getLocation().subtract(0, 1, 0), 2.0, Particle.REDSTONE, 50);
```

## Ліцензія

Цей проект ліцензовано під MIT License - дивіться файл [LICENSE](LICENSE) для деталей.