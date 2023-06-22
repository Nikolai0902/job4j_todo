package ru.job4j.todo.service;

import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> create(User user);

    boolean update(User user);

    boolean delete(int id);

    List<User> findAllOrderById();

    Optional<User> findById(int id);

    Optional<User> findByLoginAndPW(String login, String password);
}
