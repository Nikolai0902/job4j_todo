package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.SimpleTaskStore;
import ru.job4j.todo.store.SimpleUserStore;

import java.util.List;
import java.util.Optional;

@Service
public class SimpleUserService implements UserService {

    private final SimpleUserStore userStore;

    public SimpleUserService(SimpleUserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public Optional<User> create(User user) {
        return userStore.create(user);
    }

    @Override
    public boolean update(User user) {
        return userStore.update(user);
    }

    @Override
    public boolean delete(int id) {
        return userStore.delete(id);
    }

    @Override
    public List<User> findAllOrderById() {
        return userStore.findAllOrderById();
    }

    @Override
    public Optional<User> findById(int id) {
        return userStore.findById(id);
    }

    @Override
    public Optional<User> findByLoginAndPW(String login, String password) {
        return userStore.findByLoginAndPW(login, password);
    }
}
