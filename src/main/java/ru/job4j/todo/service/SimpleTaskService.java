package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.SimpleTaskStore;

import java.util.List;
import java.util.Optional;

/**
 * Класс сервис - задача.
 * @author Buslaev
 */
@Service
public class SimpleTaskService implements TaskService {

    private final SimpleTaskStore taskStore;

    public SimpleTaskService(SimpleTaskStore taskStore) {
        this.taskStore = taskStore;
    }

    @Override
    public Task add(Task task) {
        return taskStore.create(task);
    }

    @Override
    public boolean update(Task task) {
        return taskStore.update(task);
    }

    @Override
    public boolean updateDone(int id) {
        return taskStore.updateDone(id);
    }

    @Override
    public boolean delete(int id) {
        return taskStore.delete(id);
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskStore.findById(id);
    }

    @Override
    public List<Task> allTask() {
        return taskStore.findAllOrderById();
    }

    @Override
    public List<Task> allTaskTrueOrFalse(boolean flag) {
        return taskStore.findAllDoneTrueOrFalse(flag);
    }
}
