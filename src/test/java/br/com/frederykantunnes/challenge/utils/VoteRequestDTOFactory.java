package br.com.frederykantunnes.challenge.utils;

import br.com.frederykantunnes.challenge.dto.VoteRequestDTO;
import br.com.frederykantunnes.challenge.enums.VoteOptionsEnum;

public class VoteRequestDTOFactory {
    public static VoteRequestDTO buildVoteRequestDTO() {
        VoteRequestDTO data = new VoteRequestDTO();
        data.setVote(VoteOptionsEnum.SIM);
        data.setDocument("999.999.999-99");
        return data;
    }
}
