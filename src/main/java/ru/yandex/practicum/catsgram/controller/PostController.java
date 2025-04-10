package ru.yandex.practicum.catsgram.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.exception.ParameterNotValidException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.Collection;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Collection<Post> findAll(@RequestParam(defaultValue = "0") int from,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "asc") String sort) {
        if (size < 0) {
            throw new ParameterNotValidException("size", "Некорректный размер выборки. Размер должен быть больше нуля");
        }

        if (from < 0) {
            throw new ParameterNotValidException("from", "Некорректный размер выборки. Размер должен быть больше нуля");
        }

        if (!(sort.equals("asc") || sort.equals("desc"))) {
            throw new ParameterNotValidException("sort", "Неверный формат сортировки: введите asc или desc");
        }

        return postService.findAll(from, size, sort);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @PutMapping
    public Post update(@RequestBody Post newPost) {
        return postService.update(newPost);
    }

    @GetMapping("/post/{postId}")
    public Post findPost(@PathVariable("postId") Integer postId) {
        return postService.findPostById(postId)
                .orElseThrow(() -> new NotFoundException(String.format("Пост № %d не найден", postId)));
    }
}