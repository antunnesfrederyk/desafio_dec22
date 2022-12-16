package br.com.frederykantunnes.challenge.model;

import br.com.frederykantunnes.challenge.enums.VoteOptionsEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class VoteCountModel {

    private VoteOptionsEnum vote;
    private Long total;


}
