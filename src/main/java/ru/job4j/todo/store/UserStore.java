package ru.job4j.todo.store;

import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStore {

    Optional<User> create(User user);

    boolean update(User user);

    boolean delete(int userId);

    List<User> findAllOrderById();

    Optional<User> findById(int userId);

    Optional<User> findByLoginAndPW(String login, String password);
}
