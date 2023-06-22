package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория - задача.
 * @author Buslaev
 */
public interface TaskStore {

    Task create(Task task);

    boolean update(Task task);

    boolean updateDone(int id);

    boolean delete(int taskId);

    Optional<Task> findById(int taskId);

    List<Task> findAllOrderById();

    List<Task> findAllDoneTrueOrFalse(boolean flag);
}
