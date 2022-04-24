package com.meli.adn.analizer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.adn.analizer.adapter.dto.AdnDTO;
import com.meli.adn.analizer.adapter.interfaces.IAdapter;
import com.meli.adn.analizer.commons.Body;
import com.meli.adn.analizer.commons.Request;
import com.meli.adn.analizer.commons.Response;
import com.meli.adn.analizer.commons.Status;
import com.meli.adn.analizer.controller.dto.MutantResponseDTO;
import com.meli.adn.analizer.controller.dto.StatsResponseDTO;
import com.meli.adn.analizer.engine.command.ICommandBus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AdnControllerTest {

    @Autowired
    private WebApplicationContext context;

    @InjectMocks
    @Autowired
    public AdnController adnController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ICommandBus commandBus;

    @MockBean
    @Qualifier("StatsAdapter")
    private IAdapter<Response<StatsResponseDTO>, Request<Serializable>> statsAdapter;

    @MockBean
    @Qualifier("MutantAdapter")
    private IAdapter<Response<MutantResponseDTO>, Request<AdnDTO>> mutantAdapter;

    private MockMvc mockMvc;

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

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void mutantAnalyzerHttp200() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        Mockito.when(mutantAdapter.callService(any())).thenReturn(Response.<MutantResponseDTO>builder()
                .data((MutantResponseDTO)getMutantResponse().getData())
                .status(Status.builder().code(HttpStatus.OK.value()).build()).build());

        when(commandBus.handle(any())).thenReturn(getMutantResponse());
        MvcResult mvcResult = mockMvc
                .perform(post("/mutant/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Request.<String[]>builder().dna(DNA_MUTANT).build()))
                        .headers(headers)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void mutantAnalyzerHttp403() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        Mockito.when(mutantAdapter.callService(any())).thenReturn(Response.<MutantResponseDTO>builder()
                .data((MutantResponseDTO) getHumanResponse().getData())
                .status(Status.builder().code(HttpStatus.FORBIDDEN.value()).build()).build());

        when(commandBus.handle(any())).thenReturn(getMutantResponse());
        MvcResult mvcResult = mockMvc
                .perform(post("/mutant/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Request.<String[]>builder().dna(DNA_HUMAN).build()))
                        .headers(headers)
                )
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void getStats() throws Exception {
        Mockito.when(statsAdapter.callService(any())).thenReturn(Response.<StatsResponseDTO>builder().data(null).status(null).build());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        when(commandBus.handle(any())).thenReturn(getStatsResponse());
        MvcResult mvcResult = mockMvc
                .perform(get("/stats/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    private Response<Body> getStatsResponse() {
        return Response.builder().data(
                StatsResponseDTO.builder()
                        .ratio(0.4)
                        .countHuman(100)
                        .countMutant(40)
                        .build()
        ).build();
    }

    private Response<Body> getMutantResponse() {
        return Response.builder().data(
                MutantResponseDTO.builder()
                        .isMutant(true)
                        .build()
        ).status(Status.builder()
                .code(HttpStatus.OK.value())
                .description(HttpStatus.OK.getReasonPhrase())
                .build())
                .build();
    }

    private Response<Body> getHumanResponse() {
        return Response.builder().data(
                        MutantResponseDTO.builder()
                                .isMutant(false)
                                .build()
                ).status(Status.builder()
                        .code(HttpStatus.FORBIDDEN.value())
                        .description(HttpStatus.FORBIDDEN.getReasonPhrase())
                        .build())
                .build();
    }
}