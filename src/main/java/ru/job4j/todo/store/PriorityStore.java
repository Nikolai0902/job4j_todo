package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PriorityStore {

    private final CrudRepository crudRepository;

    public List<Priority> findAll() {
        return crudRepository.query("from Priority ORDER BY id", Priority.class);
    }

    public List<Priority> findByName(String name) {
        return crudRepository.query("from Priority as i where i.name = :fName",
                Priority.class, Map.of("fName", name));
    }

    public Optional<Priority> findById(int id) {
        return crudRepository.optional("from Priority as i where i.id = :fId",
                Priority.class, Map.of("fId", id));
    }
}
