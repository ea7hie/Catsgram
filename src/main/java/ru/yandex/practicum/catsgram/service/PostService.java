package ru.yandex.practicum.catsgram.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.Post;

import java.time.Instant;
import java.util.*;

// Указываем, что класс PostService - является бином и его
// нужно добавить в контекст приложения
@Slf4j
@Service
public class PostService {
    private final Map<Long, Post> posts = new HashMap<>();
    private final UserService userService;

    public PostService(UserService userService) {
        this.userService = userService;
    }

    public Collection<Post> findAll(int from, int size, String sort) {
        if (from < 0 || size < 0) {
            log.error("from или size < 0");
            throw new ConditionsNotMetException("Элемент не может быть отрицательным");
        }

        if (from > posts.size()) {
            log.error("from is too large");
            throw new ConditionsNotMetException("В вашей ленте пок что нет такого количества постов");
        }

        if (!(sort.equals("asc") || sort.equals("desc"))) {
            log.error("sort is not asc or desc; sort is {}", sort);
            throw new ConditionsNotMetException("Неверный формат сортировки");
        }

        List<Post> list;
        if (sort.equals("desc")) {
            list = posts.values().stream()
                    .sorted((p0, p1) -> Math.toIntExact(p1.getId() - p0.getId()))
                    .toList();
        } else {
            list = posts.values().stream().sorted(Comparator.comparing(Post::getId)).toList();
        }

        if (from == 0) {
            log.info("from is 0; should get latest posts");
            if (size > list.size()) {
                return list;
            }
            return list.subList(list.size() - size, list.size());
        }

        return (from + size > list.size()) ? list.subList(from, list.size()) : list.subList(from, from + size);
    }

    public Post create(Post post) {
        if (post.getDescription() == null || post.getDescription().isBlank()) {
            log.error("description is null or is blank");
            throw new ConditionsNotMetException("Описание не может быть пустым");
        }

        if (userService.findUserById(post.getAuthorId()).isEmpty()) {
            log.error("not founded user with id {}", post.getAuthorId());
            throw new ConditionsNotMetException(String.format("Автор с id = %d не найден", post.getAuthorId()));
        }

        post.setId(getNextId());
        post.setPostDate(Instant.now());
        posts.put(post.getId(), post);
        return post;
    }

    public Post update(Post newPost) {
        if (newPost.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        if (posts.containsKey(newPost.getId())) {
            Post oldPost = posts.get(newPost.getId());
            if (newPost.getDescription() == null || newPost.getDescription().isBlank()) {
                throw new ConditionsNotMetException("Описание не может быть пустым");
            }
            oldPost.setDescription(newPost.getDescription());
            return oldPost;
        }
        throw new NotFoundException("Пост с id = " + newPost.getId() + " не найден");
    }

    public Optional<Post> findPostById(long postId) {
        return (posts.containsKey(postId) ? Optional.of(posts.get(postId)) : Optional.empty());
    }

   /* public Collection<Post> findPostsByUser(String authorId, Integer size, String sort) {
        return findPostsByUser(authorId)
                .stream()
                .sorted((p0, p1) -> {
                    int comp = p0.getPostDate().compareTo(p1.getPostDate()); //прямой порядок сортировки
                    if (sort.equals("desc")) {
                        comp = -1 * comp; //обратный порядок сортировки
                    }
                    return comp;
                })
                .limit(size)
                .toList();
    }

    public Collection<Post> findPostsByUser(String userId) {
        User user = userService.findUserById(Integer.parseInt(userId))
                .orElseThrow(() -> new NotFoundException("Пользователь с идентификатором " + userId + " не найден."));

        return posts.values().stream()
                .filter(post -> post.getAuthorId() == Integer.parseInt((userId)))
                .toList();
    }*/

    private long getNextId() {
        long currentMaxId = posts.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}