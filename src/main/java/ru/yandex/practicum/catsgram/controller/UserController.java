package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping
    public User createNewUser(@RequestBody User newUser) {
        return userService.createNewUser(newUser);
    }

    @PutMapping
    public User updateUser(@RequestBody User newUser) {
        return userService.updateUser(newUser);
    }


    @GetMapping("/user/{userId}")
    public User findUser(@PathVariable("userId") Integer userId) {
        return userService.findUserById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь № %d не найден", userId)));
    }
}