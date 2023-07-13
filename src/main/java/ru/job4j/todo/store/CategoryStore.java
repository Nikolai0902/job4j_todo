package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Map;

/**
 * Класс репозитория - категории.
 * Реализация методов работы с обьектом Category,
 * через язык Hibernate Query Language (далее - HQL).
 *
 * @author Buslaev
 */
@Repository
@AllArgsConstructor
public class CategoryStore {

    private final CrudRepository crudRepository;

    public List<Category> findAll() {
        return crudRepository.query("from Category ORDER BY id", Category.class);
    }

    public List<Category> findById(int id) {
        return crudRepository.query("from Category as i where i.id = :fId",
                Category.class, Map.of("fId", id));
    }

    public List<Category> findByLislId(List<Integer> listId) {
        return crudRepository.query("from Category as i where i.id IN :fId",
                Category.class, Map.of("fId", listId));
    }
}
