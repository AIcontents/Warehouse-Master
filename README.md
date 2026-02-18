# Warehouse Master — система складского учета (Java SE)

Это учебное консольное приложение для практики по **Java SE 17+**.

## Цель проекта

Показать владение базовыми инструментами Java без фреймворков:

- ООП (наследование, абстракции, инкапсуляция)
- Коллекции (`Map`, `List`)
- Обработка исключений
- Потоки данных и работа с файлами
- Streams API и лямбда-выражения
- Многопоточность

## Что реализовано

- Базовый абстрактный класс `AbstractProduct`:
    - `id`, `name`, `price`, `quantity`.
- Наследники:
    - `FoodProduct` — с полем срока годности;
    - `ElectronicsProduct` — с полем гарантийного срока.
- Менеджер склада `Warehouse`:
    - хранение товаров в `Map<Integer, AbstractProduct>`;
    - добавление, удаление, поиск по имени;
    - фильтрация по минимальной цене;
    - расчет общего количества товаров.
- Файловое хранение:
    - сохранение/загрузка в CSV (`WarehouseFileRepository`);
    - безопасная работа с ресурсами через `try-with-resources`.
- Фоновый мониторинг:
    - отдельный поток (`WarehouseMonitor`) выполняет проверку каждые 30 секунд;
    - используется паттерн **Strategy** (`StockEventStrategy`, `ExpirationAlertStrategy`).
- Паттерны проектирования:
    - **Singleton** для `Warehouse`;
    - **Strategy** для логики фоновых событий.
- Надежный ввод в CLI:
    - приложение не падает при вводе строки вместо числа;
    - пользователь получает сообщение и повторный запрос ввода.

## Структура проекта

- `src/main/java/com/warehouse/model` — доменные классы товаров.
- `src/main/java/com/warehouse/service` — бизнес-логика склада.
- `src/main/java/com/warehouse/persistence` — чтение/запись данных.
- `src/main/java/com/warehouse/monitor` — фоновый монитор.
- `src/main/java/com/warehouse/strategy` — стратегии мониторинга.
- `src/main/java/com/warehouse/app` — точка входа и CLI.
- `src/test/java/com/warehouse/service` — JUnit-тесты.

## Запуск

```bash
mvn clean test
mvn exec:java -Dexec.mainClass="com.warehouse.app.WarehouseApplication"
```

## Тесты

В проекте есть 5 unit-тестов на ключевую бизнес-логику склада:

- добавление и поиск товара;
- защита от дублирования `id`;
- поиск по имени;
- расчет общего количества;
- удаление товара.
