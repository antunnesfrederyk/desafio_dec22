package br.com.frederykantunnes.challenge.utils;

import br.com.frederykantunnes.challenge.model.StaveModel;

import java.time.LocalDateTime;
import java.util.UUID;

public class StaveModelFactory {

    public static StaveModel buildStaveModel(String title) {
        return StaveModel.builder()
                .uuid(UUID.randomUUID().toString())
                .title(title)
                .build();
    }
    public static StaveModel buildStaveModelSaved(String uuid, String title) {
        return StaveModel.builder()
                .id(123L)
                .uuid(uuid)
                .title(title)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
