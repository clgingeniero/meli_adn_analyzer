package com.meli.adn.analizer.command.handler;

import com.meli.adn.analizer.adapter.dto.AdnDTO;
import com.meli.adn.analizer.adapter.interfaces.IAdapter;
import com.meli.adn.analizer.command.MutantReqCommand;
import com.meli.adn.analizer.commons.Request;
import com.meli.adn.analizer.commons.Response;
import com.meli.adn.analizer.commons.Status;
import com.meli.adn.analizer.controller.dto.MutantResponseDTO;
import com.meli.adn.analizer.engine.command.ICommandHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class MutantCommandTest {

    public static final String[] DNA_MUTANT = new String[]{
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
    };

    public static final String[] DNA_HUMAN = new String[]{
            "TTGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "ACCCTA",
            "TCACTG"
    };

    public static final String[] DNA_CORRUPT = new String[]{
            "UTGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "ACCCTA",
            "TCACTG"
    };

    @Autowired
    private ICommandHandler<MutantResponseDTO, MutantReqCommand> mutantCommand;

    @MockBean
    @Qualifier("MutantAdapter")
    private IAdapter<Response<MutantResponseDTO>, Request<AdnDTO>> mutantAdapter;

    private Request<String[]> request;

    @Test
    void handle_mutant_status() {
        request = Request.<String[]>builder().dna(DNA_MUTANT).build();
        Mockito.when(mutantAdapter.callService(any())).thenReturn(Response.<MutantResponseDTO>builder()
                .data(MutantResponseDTO.builder().isMutant(true).build())
                .status(Status.builder().code(HttpStatus.OK.value()).build()).build());
        Response<MutantResponseDTO> response= mutantCommand.handle(new MutantReqCommand(request));
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus().getCode());
    }

    @Test
    void handle_mutant_data() {
        request = Request.<String[]>builder().dna(DNA_MUTANT).build();
        Mockito.when(mutantAdapter.callService(any())).thenReturn(Response.<MutantResponseDTO>builder()
                .data(MutantResponseDTO.builder().isMutant(true).build())
                .status(Status.builder().code(HttpStatus.OK.value()).build()).build());
        Response<MutantResponseDTO> response= mutantCommand.handle(new MutantReqCommand(request));
        Assertions.assertEquals(true, response.getData().getIsMutant());
    }

    @Test
    void handle_human_status() {
        request = Request.<String[]>builder().dna(DNA_HUMAN).build();
        Mockito.when(mutantAdapter.callService(any())).thenReturn(Response.<MutantResponseDTO>builder()
                .data(MutantResponseDTO.builder().isMutant(false).build())
                .status(Status.builder().code(HttpStatus.FORBIDDEN.value()).build()).build());
        Response<MutantResponseDTO> response= mutantCommand.handle(new MutantReqCommand(request));
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus().getCode());
    }

    @Test
    void handle_dna_bad() {
        request = Request.<String[]>builder().dna(DNA_CORRUPT).build();
        Mockito.when(mutantAdapter.callService(any())).thenReturn(Response.<MutantResponseDTO>builder()
                .data(MutantResponseDTO.builder().isMutant(false).build())
                .status(Status.builder().code(HttpStatus.BAD_REQUEST.value()).build()).build());
        Response<MutantResponseDTO> response= mutantCommand.handle(new MutantReqCommand(request));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus().getCode());
    }

    @Test
    void handle_human_data() {
        request = Request.<String[]>builder().dna(DNA_HUMAN).build();
        Mockito.when(mutantAdapter.callService(any())).thenReturn(Response.<MutantResponseDTO>builder()
                .data(MutantResponseDTO.builder().isMutant(false).build())
                .status(Status.builder().code(HttpStatus.FORBIDDEN.value()).build()).build());
        Response<MutantResponseDTO> response= mutantCommand.handle(new MutantReqCommand(request));
        Assertions.assertEquals(false, response.getData().getIsMutant());
    }

    @Test
    void handle_error() {
        request = Request.<String[]>builder().dna(DNA_HUMAN).build();
        Mockito.when(mutantAdapter.callService(any())).thenReturn(Response.<MutantResponseDTO>builder()
                .data(null)
                .status(Status.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build()).build());
        Response<MutantResponseDTO> response= mutantCommand.handle(new MutantReqCommand(request));
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus().getCode());
    }

    @AfterEach
    void tearDown(){
        request = null;
    }

}