package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Класс репозитория - задача.
 * Реализация методов работы с обьектом Task,
 * через язык Hibernate Query Language (далее - HQL).
 *
 * @author Buslaev
 */
@Repository
@AllArgsConstructor
public class SimpleTaskStore implements TaskStore {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleTaskStore.class.getName());
    private final CrudRepository crudRepository;
    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     *
     * @param task задачу.
     * @return задачу с id.
     */
    @Override
    public Task create(Task task) {
        try {
            crudRepository.run(session -> session.persist(task));
        } catch (Exception e) {
            LOG.error("create tasks", e);
        }
        return task;
    }

    /**
     * Обновить в базе задачу.
     *
     * @param task пользователь.
     */
    @Override
    public boolean update(Task task) {
        boolean result = false;
        try {
            crudRepository.run(session -> session.merge(task));
            result = true;
        } catch (Exception e) {
            LOG.error("update tasks", e);
        }
        return result;
    }

    /**
     * Обновить только поле done у задачи.
     *
     * @param id задачи.
     */
    @Override
    public boolean updateDone(int id) {
        boolean result = false;
        try {
            crudRepository.run("UPDATE Task SET done = :fDone WHERE id = :fId", Map.of("fDone", true, "fId", id));
            result = true;
        } catch (Exception e) {
            LOG.error("done tasks", e);
        }
        return result;
    }

    /**
     * Удалить задачу по id.
     *
     * @param id ID
     */
    @Override
    public boolean delete(int id) {
        boolean result = false;
        try {
            crudRepository.run("DELETE Task WHERE id = :id", Map.of("id", id));
            result = true;
        } catch (Exception e) {
            LOG.error("done tasks", e);
        }
        return result;
    }

    /**
     * Найти задачу по ID
     *
     * @param id ID
     * @return задача.
     */
    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional("FROM Task f JOIN FETCH f.priority WHERE f.id = :id", Task.class, Map.of("id", id));
    }

    /**
     * Список задач отсортированных по id.
     *
     * @return список задач.
     */
    @Override
    public List<Task> findAllOrderById() {
        return crudRepository.query("FROM Task f JOIN FETCH f.priority order by f.id ASC", Task.class);
    }

    /**
     * Список задач выполненных или нет взависимости от flag.
     *
     * @param flag флаг
     * @return список задач.
     */
    @Override
    public List<Task> findAllDoneTrueOrFalse(boolean flag) {
        return crudRepository.query("FROM Task f JOIN FETCH f.priority WHERE f.done = :done", Task.class, Map.of("done", flag));
    }
}
