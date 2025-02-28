package ru.yandex.practicum.catsgram.model;

import lombok.*;

@EqualsAndHashCode(of = {"id"})
@Setter
@Getter
@ToString
@RequiredArgsConstructor
public class Image {
    private Long id;
    private long postId;
    private String originalFileName;
    private String filePath;
}
