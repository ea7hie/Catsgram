package ru.yandex.practicum.catsgram.model;

import lombok.*;

import java.time.Instant;

@EqualsAndHashCode(of = {"id"})
@Setter
@Getter
@ToString
@RequiredArgsConstructor
public class Post {
    private Long id;
    private long authorId;
    private String description;
    private Instant postDate;
}
