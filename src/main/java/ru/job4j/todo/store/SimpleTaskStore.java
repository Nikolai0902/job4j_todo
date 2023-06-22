package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс репозитория - задача.
 * Реализация методов работы с обьектом Task,
 * через язык Hibernate Query Language (далее - HQL).
 * @author Buslaev
 */
@Repository
@AllArgsConstructor
public class SimpleTaskStore implements TaskStore {

    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     * @param task задачу.
     * @return задачу с id.
     */
    @Override
    public Task create(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    /**
     * Обновить в базе задачу.
     * @param task пользователь.
     */
    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        boolean result = false;
        try {
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * Обновить только поле done у задачи.
     * @param id задачи.
     */
    @Override
    public boolean updateDone(int id) {
        Session session = sf.openSession();
        int result = 0;
        try {
            session.beginTransaction();
            result = session.createQuery(
                            "UPDATE Task SET done = :fDone WHERE id = :fId")
                    .setParameter("fDone", true)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result > 0;
    }

    /**
     * Удалить задачу по id.
     * @param id ID
     */
    @Override
    public boolean delete(int id) {
        Session session = sf.openSession();
        int result = 0;
        try {
            session.beginTransaction();
            result = session.createQuery("DELETE Task WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result > 0;
    }

    /**
     * Найти задачу по ID
     * @param id ID
     * @return задача.
     */
    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        Optional<Task> task = Optional.empty();
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery("FROM Task WHERE id = :id", Task.class);
            query.setParameter("id", id);
            task = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    /**
     * Список задач отсортированных по id.
     * @return список задач.
     */
    @Override
    public List<Task> findAllOrderById() {
        Session session = sf.openSession();
        List<Task> tasks = new ArrayList<>();
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery("FROM Task order by id ASC", Task.class);
            tasks = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }

    /**
     * Список задач выполненных или нет взависимости от flag.
     * @param flag флаг
     * @return список задач.
     */
    @Override
    public List<Task> findAllDoneTrueOrFalse(boolean flag) {
        Session session = sf.openSession();
        List<Task> tasks = new ArrayList<>();
        try {
            session.beginTransaction();
            String request = "";
            if (!flag) {
                request = "FROM Task WHERE done = false";
            } else {
                request = "FROM Task WHERE done = true";
            }
            Query<Task> query = session.createQuery(request, Task.class);
            tasks = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }
}
