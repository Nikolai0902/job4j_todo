package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
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
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userStore.findByLoginAndPassword(login, password);
    }
}
