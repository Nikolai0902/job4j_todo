# job4j_todo

## О проекте

**Проект "TODO список"**

На главном экране приложения представлены список всех задач, в нем отображается
имя, дату создания и состояние (выполнено или нет).
Можно добавлять задания. На странице с подробным описанием есть возможность отметить как -
"Выполнено", "Редактировать", "Удалить".

- Если нажали на кнопку выполнить, то задание переводится в состояние выполнено.
- Кнопка "Редактировать" переводит пользователя на отдельную страницу для редактирования.
- Кнопка "Удалить" удаляет задание и переходит на список всех заданий.

_Контакты для связи:_
__**kbus94@yandex.ru**__

### Для запуска необходимо:
1. [x] Java 17;
2. [x] Maven 3.8.
3. [x] PostgreSql 14.

### Стек используемых технологий:
1. Java 17;
2. PostgreSQL JDBC 42.5.4;
3. Spring boot 2.7.4;
4. Thymeleaf 3.0.15;
5. Bootstrap v5.2.2;
6. liquibase 4.15.0;

Перед запуском проекта необходимо создать **БД todo** и указать
**_login/password_** в файле _src/main/resources/db.properties_;

### Запуск приложения:
```
mvn spring-boot:run
```
### Интерфейс:
Главная страница.