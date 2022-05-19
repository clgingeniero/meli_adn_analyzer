package com.meli.adn.analizer.command.handler;

import com.meli.adn.analizer.adapter.interfaces.IAdapter;
import com.meli.adn.analizer.command.StatsReqCommand;
import com.meli.adn.analizer.commons.Request;
import com.meli.adn.analizer.commons.Response;
import com.meli.adn.analizer.commons.Status;
import com.meli.adn.analizer.controller.dto.StatsResponseDTO;
import com.meli.adn.analizer.engine.command.ICommandHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class StatsCommandTest {

    @Autowired
    private ICommandHandler<StatsResponseDTO, StatsReqCommand> statsCommand;

    @MockBean
    @Qualifier("StatsAdapter")
    private IAdapter<Response<StatsResponseDTO>, Request<Serializable>> statsAdapter;


    @Test
    void handle_mutant_status() {
        Mockito.when(statsAdapter.callService(any())).thenReturn(getResponseStats());
        Response<StatsResponseDTO> response= statsCommand.handle(new StatsReqCommand());
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus().getCode());
    }

    private Response<StatsResponseDTO> getResponseStats() {
        return Response.<StatsResponseDTO>builder()
                .data(StatsResponseDTO.builder().countMutant(100).countHuman(40).ratio(0.4).build())
                        .status(Status.builder().code(HttpStatus.OK.value()).build()).build();
    }



}