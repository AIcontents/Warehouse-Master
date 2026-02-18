# Warehouse Master (Java SE)

Консольное приложение для учебной практики по Java SE.

## Что реализовано

- ООП: абстрактный класс `AbstractProduct` и наследники `FoodProduct`, `ElectronicsProduct`.
- Инкапсуляция: все поля классов `private`, доступ через методы.
- Коллекции: хранение товаров в `Map<Integer, AbstractProduct>` + операции поиска и фильтрации.
- Streams API: фильтрация/поиск/агрегация выполнены через стримы.
- Многопоточность: фоновый поток проверяет скорое истечение срока годности каждые 30 секунд.
- Работа с файлами: сохранение/загрузка данных CSV через `try-with-resources`.
- Обработка ошибок ввода: приложение не падает при вводе некорректного числа.
- Паттерны: `Singleton` (`Warehouse`) и `Strategy` (алгоритм уведомлений мониторинга).

## Запуск

```bash
mvn clean test
mvn exec:java -Dexec.mainClass="com.warehouse.app.WarehouseApplication"
```

> Для запуска `exec:java` может понадобиться плагин `exec-maven-plugin`, либо запуск через IDE.
