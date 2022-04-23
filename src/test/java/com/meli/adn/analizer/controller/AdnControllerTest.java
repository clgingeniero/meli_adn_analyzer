package com.meli.adn.analizer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.adn.analizer.commons.Request;
import com.meli.adn.analizer.commons.Response;
import com.meli.adn.analizer.controller.dto.MutantResponseDTO;
import com.meli.adn.analizer.controller.dto.StatsResponseDTO;
import com.meli.adn.analizer.engine.command.ICommandBus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void mutantAnalyzerHttp405() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        var response = Response.builder().data(
                StatsResponseDTO.builder()
                        .ratio(0.4)
                        .countHuman(100)
                        .countMutant(40)
                        .build()
        ).build();
        when(commandBus.handle(any())).thenReturn(response);
        MvcResult mvcResult = mockMvc
                .perform(get("/mutant/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers)
                )
                .andExpect(status().isMethodNotAllowed())
                .andReturn();
        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void mutantAnalyzerHttp200() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        var response = Response.builder().data(
                MutantResponseDTO.builder()
                        .isMutant(true)
                        .build()
        ).build();
        when(commandBus.handle(any())).thenReturn(response);
        MvcResult mvcResult = mockMvc
                .perform(post("/mutant/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Request.<String[]>builder().dna(dna).build()))
                        .headers(headers)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void getStats() throws Exception {
        HttpHeaders headers = new HttpHeaders();

        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        var response = Response.builder().data(
                StatsResponseDTO.builder()
                        .ratio(0.4)
                        .countHuman(100)
                        .countMutant(40)
                        .build()
                ).build();
        when(commandBus.handle(any())).thenReturn(response);
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
}