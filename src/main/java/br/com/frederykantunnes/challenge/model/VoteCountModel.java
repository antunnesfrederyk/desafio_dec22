package br.com.frederykantunnes.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class VoteCountModel {

    private String vote;
    private Long total;


}
