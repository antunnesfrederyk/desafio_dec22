package br.com.frederykantunnes.challenge.utils;

import br.com.frederykantunnes.challenge.dto.VoteRequestDTO;
import br.com.frederykantunnes.challenge.enums.VoteOptionsEnum;
import br.com.frederykantunnes.challenge.model.VoteModel;

import java.time.LocalDateTime;

public class VoteModelFactory {
    public static VoteModel buildVoteModel(VoteModel voteModel) {
        VoteModel data = voteModel;
        data.setCreatedAt(LocalDateTime.now());
        data.setId(123L);
        data.setVote(VoteOptionsEnum.SIM);
        data.setDocument("999.999.999-99");
        return data;
    }
    public static VoteModel buildVoteModel(VoteRequestDTO vote) {
        return VoteModel.builder()
                .document(vote.getDocument())
                .build();
    }
}
