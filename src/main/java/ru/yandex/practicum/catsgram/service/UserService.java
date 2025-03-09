package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final Map<Long, User> users = new HashMap<>();
    private long idForNewUser = 0;

    public Collection<User> findAllUsers() {
        return users.values();
    }

    public User createNewUser(@RequestBody User newUser) {
        if (newUser.getEmail() == null || newUser.getEmail().isBlank()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        }

        if (isEmailAlreadyUsed(newUser.getEmail())) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }

        newUser.setId(getNextId());
        newUser.setRegistrationDate(Instant.now());
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    public User updateUser(@RequestBody User newUser) {
        if (newUser.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }

        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());

            //check for update email
            if (newUser.getEmail() != null && !oldUser.getEmail().equals(newUser.getEmail())) {
                if (isEmailAlreadyUsed(newUser.getEmail())) {
                    throw new DuplicatedDataException("Этот имейл уже используется");
                }
                oldUser.setEmail(newUser.getEmail());
            }

            oldUser.setUsername(newUser.getUsername() == null ? oldUser.getUsername() : newUser.getUsername());
            oldUser.setUsername(newUser.getPassword() == null ? oldUser.getPassword() : newUser.getPassword());

            return oldUser;
        }

        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    public Optional<User> findUserById(long idForSearch) {
        return (users.containsKey(idForSearch) ? Optional.of(users.get(idForSearch)) : Optional.empty());
    }

    private long getNextId() {
        return ++idForNewUser;
    }

    private boolean isEmailAlreadyUsed(String emailForCheck) {
        return users.values().stream()
                .map(User::getEmail)
                .toList()
                .contains(emailForCheck);
    }
}
