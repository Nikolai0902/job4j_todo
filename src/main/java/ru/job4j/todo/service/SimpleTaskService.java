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
    public void update(Task task) {
        taskStore.update(task);
    }

    @Override
    public void delete(int id) {
        taskStore.delete(id);
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
    public List<Task> allTaskTrue() {
        return taskStore.findAllDoneTrue();
    }

    @Override
    public List<Task> allTaskFalse() {
        return taskStore.findAllDoneFalse();
    }

}
