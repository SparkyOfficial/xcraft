# xcraft

[![en](https://img.shields.io/badge/lang-en-red.svg)](README.md)
[![ru](https://img.shields.io/badge/lang-ru-yellow.svg)](README.ru.md)
[![uk](https://img.shields.io/badge/lang-uk-blue.svg)](README.uk.md)

xcraft - это библиотека для разработки плагинов Minecraft, которая расширяет стандартный Bukkit API, делая его более удобным и мощным.

Символ "x" в названии означает "eXtended" (расширенный), "eXpressive" (выразительный) и "eXperimental" (экспериментальный).

## Философия и ключевые принципы

1. **Модульность**: Библиотека состоит из независимых модулей. Разработчик подключает только то, что ему нужно.
2. **Fluent API**: Весь API строится на цепочках вызовов (builder'ах), что делает код читаемым и лаконичным.
3. **Версионная независимость**: Максимальная абстракция от прямого использования NMS.
4. **Производительность**: Использование самых быстрых и проверенных решений для "тяжелых" операций.
5. **Открытость**: Библиотека является Open-Source с хорошей документацией.

## Структура модулей

### xcraft-core
Базовый модуль, от которого зависят все остальные.

- **XSchedule**: Улучшенный планировщик задач
- **ConfigWrapper**: Обертка над FileConfiguration
- **Пользовательские события**: AsyncPlayerMoveEvent, PlayerJumpEvent, PlayerUseItemEvent

### xcraft-ui
Модуль для создания элементов интерфейса.

- **GuiBuilder**: Конструктор GUI-меню
- **MessageBuilder**: Конструктор сообщений
- **DisplayManager**: Менеджер отображения (BossBar, ActionBar, Title, Scoreboard)

### xcraft-items
Все для работы с предметами.

- **ItemBuilder**: Создание ItemStack через цепочку вызовов
- **NBT API**: Версионно-независимый способ работы с NBT-тегами
- **Сериализация**: Преобразование ItemStack в строку и обратно

### xcraft-world
Для манипуляций с миром и эффектами.

- **WorldEdit wrapper**: Упрощенный API для FastAsyncWorldEdit
- **Particle drawer**: API для создания эффектов из частиц
- **Region manager**: Работа с регионами

### xcraft-network (Опциональный)
Абстракции над низкоуровневыми вещами.

- **Packet wrapper**: Упрощенный API для ProtocolLib
- **NPC API**: API для создания неигровых персонажей

## Установка

Добавьте зависимость в ваш `pom.xml`:

```xml
<dependencies>
    <!-- Добавьте нужные модули xcraft -->
    <dependency>
        <groupId>io.github.sparky983</groupId>
        <artifactId>xcraft-core</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

## Примеры использования

### XSchedule
```java
// Вместо громоздкого BukkitRunnable
XSchedule.timer(20, 20, () -> {
    Bukkit.broadcastMessage("Tick-Tock!");
}).cancelAfter(10); // Остановится после 10 выполнений
```

### GuiBuilder
```java
GuiBuilder.create("§8Меню навигации", 3)
    .item(11, ItemBuilder.of(Material.COMPASS).name("§aТелепорт на спавн").build())
    .onClick(event -> {
        event.getWhoClicked().teleport(spawnLocation);
        event.getWhoClicked().sendMessage("Вы телепортированы на спавн!");
    })
    .lock() // Запретить брать предметы
    .open(player);
```

### ItemBuilder и NBT API
```java
ItemStack magicSword = ItemBuilder.of(Material.DIAMOND_SWORD)
    .name("§bЛегендарный Клинок")
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
// Нарисовать красный круг под игроком
ParticleDrawer.circle(player.getLocation().subtract(0, 1, 0), 2.0, Particle.REDSTONE, 50);
```

## Лицензия

Этот проект лицензирован под MIT License - смотрите файл [LICENSE](LICENSE) для деталей.