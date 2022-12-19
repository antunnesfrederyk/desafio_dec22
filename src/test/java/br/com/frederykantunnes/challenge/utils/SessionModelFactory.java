package br.com.frederykantunnes.challenge.utils;

import br.com.frederykantunnes.challenge.model.SessionModel;
import org.apache.kafka.common.Uuid;

import java.time.LocalDateTime;

public class SessionModelFactory {
    public static SessionModel buildStartSessionRequestDTO(SessionModel sessionModel) {
        var data = sessionModel;
        data.setId(123L);
        data.setCreatedAt(LocalDateTime.now());
        return data;
    }
    public static SessionModel buildModel() {
        return SessionModel.builder()
                .id(123L)
                .uuid(Uuid.randomUuid().toString())
                .uuidStave(Uuid.randomUuid().toString())
                .durationInMinutes(1)
                .createdAt(LocalDateTime.now())
                .totalNegativeVotes(0)
                .totalPositiveVotes(0)
                .votesCounted(true)
                .build();
    }

    public static SessionModel buildInvalidTimeSession() {
        return SessionModel.builder()
                .id(123L)
                .uuid(Uuid.randomUuid().toString())
                .uuidStave(Uuid.randomUuid().toString())
                .durationInMinutes(1)
                .createdAt(LocalDateTime.now().minusMinutes(2))
                .totalNegativeVotes(0)
                .totalPositiveVotes(0)
                .votesCounted(true)
                .build();
    }
}
