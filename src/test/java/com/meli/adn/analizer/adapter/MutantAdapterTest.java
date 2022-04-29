package com.meli.adn.analizer.adapter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.meli.adn.analizer.adapter.dto.AdnDTO;
import com.meli.adn.analizer.adapter.interfaces.IAdapter;
import com.meli.adn.analizer.adapter.model.Adn;
import com.meli.adn.analizer.commons.Request;
import com.meli.adn.analizer.commons.Response;
import com.meli.adn.analizer.controller.dto.MutantResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class MutantAdapterTest {

    private IAdapter<Response<MutantResponseDTO>, Request<AdnDTO>> mutant;

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

    private MutantAdapter mutantAdapter;

    @Mock
    protected DynamoDBMapper dynamoDBMapper;


    @BeforeEach
    public void setUp() {
        dynamoDBMapper = Mockito.mock(DynamoDBMapper.class);
        mutantAdapter = new MutantAdapter("Adn");
        mutantAdapter.dynamoDBMapper = dynamoDBMapper;
    }

    @Test
    void mutant() {
        Adn adn = new Adn();
        adn.setDna(Arrays.toString(DNA_MUTANT));
        adn.setMutant(true);
        Mockito.when(dynamoDBMapper.load(any())).thenReturn(adn);
        var response = mutantAdapter.callService(Request.<AdnDTO>builder().dna(AdnDTO.builder().adnEval(DNA_MUTANT).isMutant(true).build()).build());
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus().getCode());
    }

    @Test
    void human() {
        Adn adn = new Adn();
        adn.setDna(Arrays.toString(DNA_HUMAN));
        adn.setMutant(false);
        Mockito.when(dynamoDBMapper.load(any())).thenReturn(adn);
        var response = mutantAdapter.callService(Request.<AdnDTO>builder().dna(AdnDTO.builder().adnEval(DNA_HUMAN).isMutant(false).build()).build());
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus().getCode());
    }

}
