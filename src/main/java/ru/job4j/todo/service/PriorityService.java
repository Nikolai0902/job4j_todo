package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.store.PriorityStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PriorityService {

    private final PriorityStore priorityStore;

    public List<Priority> findAll() {
        return priorityStore.findAll();
    }

    public List<Priority> findByName(String name) {
        return priorityStore.findByName(name);
    }

    public Optional<Priority> findById(int id) {
        return priorityStore.findById(id);
    }
}
