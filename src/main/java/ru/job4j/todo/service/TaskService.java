package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс сервис - задача.
 * @author Buslaev
 */
public interface TaskService {

    Task add(Task task);

    boolean update(Task task);

    boolean updateDone(int id);

    boolean delete(int id);

    Optional<Task> findById(int id);

    List<Task> allTask();

    List<Task> allTaskTrueOrFalse(boolean flag);
}
