package br.com.frederykantunnes.challenge.service;

import br.com.frederykantunnes.challenge.dto.StartSessionResponseDTO;
import br.com.frederykantunnes.challenge.exceptions.BadRequestException;
import br.com.frederykantunnes.challenge.exceptions.response.ErrorCodeEnum;
import br.com.frederykantunnes.challenge.mapper.SessionMapper;
import br.com.frederykantunnes.challenge.repository.SessionRepository;
import br.com.frederykantunnes.challenge.utils.SessionModelFactory;
import br.com.frederykantunnes.challenge.utils.StartSessionRequestDTOFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @InjectMocks
    private SessionService tested;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private SessionMapper mapper;

    @Test
    @DisplayName("Should successfully start voting session")
    void shouldSuccessfullyStartVotingSession(){
        var localMapper = new SessionMapper();

        var request = StartSessionRequestDTOFactory.buildStartSessionRequestDTOWithDuration(UUID.randomUUID().toString());
        var model = localMapper.startSessionToSessionModel(request);
        var modelSaved = SessionModelFactory.buildStartSessionRequestDTO(model);
        var response = localMapper.sessionModelToResponse(modelSaved);

        when(mapper.startSessionToSessionModel(request)).thenReturn(model);
        when(mapper.sessionModelToResponse(modelSaved)).thenReturn(response);
        when(sessionRepository.findByUuidStave(request.getUuidStave())).thenReturn(Optional.empty());
        when(sessionRepository.save(model)).thenReturn(modelSaved);

        StartSessionResponseDTO startSessionResponseDTO = tested.startSession(request);


        verify(sessionRepository).findByUuidStave(request.getUuidStave());
        verify(mapper).startSessionToSessionModel(request);
        verify(mapper).sessionModelToResponse(modelSaved);
        assertEquals(response, startSessionResponseDTO);
        assertEquals(request.getMinutesDuration(), startSessionResponseDTO.getMinutesDuration());
    }


    @Test
    @DisplayName("Should successfully start voting session with default duration")
    void shouldSuccessfullyStartVotingSessionWithDefaultDuration(){
        var localMapper = new SessionMapper();

        var request = StartSessionRequestDTOFactory.buildStartSessionRequestDTOWithoutDuration(UUID.randomUUID().toString());
        var model = localMapper.startSessionToSessionModel(request);
        var modelSaved = SessionModelFactory.buildStartSessionRequestDTO(model);
        var response = localMapper.sessionModelToResponse(modelSaved);

        when(mapper.startSessionToSessionModel(request)).thenReturn(model);
        when(mapper.sessionModelToResponse(modelSaved)).thenReturn(response);
        when(sessionRepository.findByUuidStave(request.getUuidStave())).thenReturn(Optional.empty());
        when(sessionRepository.save(model)).thenReturn(modelSaved);

        StartSessionResponseDTO startSessionResponseDTO = tested.startSession(request);

        verify(sessionRepository).findByUuidStave(request.getUuidStave());
        verify(mapper).startSessionToSessionModel(request);
        verify(mapper).sessionModelToResponse(modelSaved);
        assertEquals(response, startSessionResponseDTO);
        assertNull(request.getMinutesDuration());
        assertEquals(startSessionResponseDTO.getMinutesDuration(), 1);
    }

    @Test
    @DisplayName("Should not start a voting session and throw a bad request exception")
    void shouldNotStartVotingSessionAndThrowBadRequestException(){
        var request = StartSessionRequestDTOFactory.buildStartSessionRequestDTOWithoutDuration(UUID.randomUUID().toString());

        when(sessionRepository.findByUuidStave(request.getUuidStave())).thenReturn(Optional.of(SessionModelFactory.buildModel()));

        BadRequestException badRequestException = null;

        try {
            tested.startSession(request);
        }catch (BadRequestException ex){
            badRequestException = ex;
        }

        verify(sessionRepository).findByUuidStave(request.getUuidStave());
        verify(mapper, never()).sessionModelToResponse(any());
        verify(mapper, never()).startSessionToSessionModel(any());
        verify(sessionRepository, never()).save(any());
        assertEquals(Objects.requireNonNull(badRequestException).getMessage(), "Session has already started");
        assertEquals(Objects.requireNonNull(badRequestException).getErrorCode(), ErrorCodeEnum.E0002);

    }

}
