package br.com.frederykantunnes.challenge.mapper;

import br.com.frederykantunnes.challenge.dto.VoteRequestDTO;
import br.com.frederykantunnes.challenge.dto.VoteResponseDTO;
import br.com.frederykantunnes.challenge.model.VoteModel;

public class VoteMapper {
    public static VoteModel createVoteModel(String uuisSession, VoteRequestDTO vote) {
        return VoteModel.builder()
                .uuidSession(uuisSession)
                .vote(vote.getVote().toUpperCase())
                .document(vote.getDocument())
                .build();
    }
    public static VoteResponseDTO generateResponseDto(VoteModel model, String status) {
        return VoteResponseDTO.builder()
                .uuid(model.getUuid())
                .status(status)
                .build();
    }

}
