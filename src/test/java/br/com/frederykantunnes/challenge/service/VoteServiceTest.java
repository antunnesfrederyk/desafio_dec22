package br.com.frederykantunnes.challenge.service;

import br.com.frederykantunnes.challenge.api.DocumentValidatorAPI;
import br.com.frederykantunnes.challenge.dto.VoteResponseDTO;
import br.com.frederykantunnes.challenge.exceptions.BadRequestException;
import br.com.frederykantunnes.challenge.exceptions.DuplicatedException;
import br.com.frederykantunnes.challenge.exceptions.NotFountDataException;
import br.com.frederykantunnes.challenge.mapper.VoteMapper;
import br.com.frederykantunnes.challenge.repository.SessionRepository;
import br.com.frederykantunnes.challenge.repository.VoteRepository;
import br.com.frederykantunnes.challenge.utils.SessionModelFactory;
import br.com.frederykantunnes.challenge.utils.VoteModelFactory;
import br.com.frederykantunnes.challenge.utils.VoteRequestDTOFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {

    @InjectMocks
    private VoteService tested;


    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private VoteMapper mapper;

    @Mock
    private DocumentValidatorAPI validatorAPI;


    @Test
    @DisplayName("Should successfully register vote")
    void shouldSuccessfullyRegisterVote(){
        var localMapper = new VoteMapper();

        String uuidSession = UUID.randomUUID().toString();
        var request = VoteRequestDTOFactory.buildVoteRequestDTO();
        var session = SessionModelFactory.buildModel();
        var model = localMapper.createVoteModel(uuidSession,request);
        var modelSaved = VoteModelFactory.buildVoteModel(model);
        var response = localMapper.generateResponseDto(modelSaved,"Voto realizado com Sucesso!");

        when(sessionRepository.findByUuid(uuidSession)).thenReturn(Optional.of(session));
        when(voteRepository.findByUuidSessionAndDocument(uuidSession, request.getDocument())).thenReturn(Optional.empty());
        when(mapper.createVoteModel(uuidSession, request)).thenReturn(model);
        when(voteRepository.save(model)).thenReturn(modelSaved);
        when(mapper.generateResponseDto(modelSaved, response.getStatus())).thenReturn(response);

        VoteResponseDTO vote = tested.vote(uuidSession, request);

        verify(sessionRepository).findByUuid(uuidSession);
        verify(voteRepository).findByUuidSessionAndDocument(uuidSession, request.getDocument());
        verify(mapper).createVoteModel(uuidSession, request);
        verify(voteRepository).save(model);
        verify(mapper).generateResponseDto(modelSaved, response.getStatus());
        assertEquals(vote, response);
    }


    @Test
    @DisplayName("Should not register a vote with invalid session and throw an exception")
    void shouldNotRegisterVotewithInvalidSessionAndThrowException(){
        String uuidSession = UUID.randomUUID().toString();
        var request = VoteRequestDTOFactory.buildVoteRequestDTO();

        when(sessionRepository.findByUuid(uuidSession)).thenReturn(Optional.empty());

        NotFountDataException exception = null;
        try {
            tested.vote(uuidSession, request);
        }catch (NotFountDataException ex){
            exception = ex;
        }

        verify(sessionRepository).findByUuid(uuidSession);
        verify(voteRepository, never()).findByUuidSessionAndDocument(any(), any());
        verify(voteRepository, never()).save(any());
        verify(mapper, never()).createVoteModel(any(), any());
        verify(mapper, never()).generateResponseDto(any(), any());
        assertEquals(Objects.requireNonNull(exception).getMessage(), "Session not found!");
    }


    @Test
    @DisplayName("Should not register a vote with session finished time and throw an exception")
    void shouldNotRegisterVotewithSessionFinishedTimeAndThrowException(){
        String uuidSession = UUID.randomUUID().toString();
        var request = VoteRequestDTOFactory.buildVoteRequestDTO();
        var session = SessionModelFactory.buildInvalidTimeSession();

        when(sessionRepository.findByUuid(uuidSession)).thenReturn(Optional.of(session));

        BadRequestException exception = null;
        try {
            tested.vote(uuidSession, request);
        }catch (BadRequestException ex){
            exception = ex;
        }

        verify(sessionRepository).findByUuid(uuidSession);
        verify(voteRepository, never()).findByUuidSessionAndDocument(any(), any());
        verify(voteRepository, never()).save(any());
        verify(mapper, never()).createVoteModel(any(), any());
        verify(mapper, never()).generateResponseDto(any(), any());
        assertEquals(Objects.requireNonNull(exception).getMessage(), "Session is finished");
    }


    @Test
    @DisplayName("Should not register a vote vote already taken and throw an exception")
    void shouldNotRegisterVoteAlreadyTakenAndThrowException(){
        String uuidSession = UUID.randomUUID().toString();
        var request = VoteRequestDTOFactory.buildVoteRequestDTO();
        var session = SessionModelFactory.buildModel();
        var voteFind = VoteModelFactory.buildVoteModel(request);

        when(sessionRepository.findByUuid(uuidSession)).thenReturn(Optional.of(session));
        when(voteRepository.findByUuidSessionAndDocument(uuidSession, request.getDocument())).thenReturn(Optional.of(voteFind));

        DuplicatedException exception = null;
        try {
            tested.vote(uuidSession, request);
        }catch (DuplicatedException ex){
            exception = ex;
        }

        verify(sessionRepository).findByUuid(uuidSession);
        verify(voteRepository).findByUuidSessionAndDocument(uuidSession, request.getDocument());
        verify(voteRepository, never()).save(any());
        verify(mapper, never()).createVoteModel(any(), any());
        verify(mapper, never()).generateResponseDto(any(), any());
        assertEquals(Objects.requireNonNull(exception).getMessage(), "Document has vote for current session");
    }

}
