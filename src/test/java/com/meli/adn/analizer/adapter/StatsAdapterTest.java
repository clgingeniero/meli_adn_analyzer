package com.meli.adn.analizer.adapter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class StatsAdapterTest {

    private StatsAdapter statsAdapter;

    @Mock
    protected DynamoDBMapper dynamoDBMapper;

    @BeforeEach
    public void setUp() {
        dynamoDBMapper = Mockito.mock(DynamoDBMapper.class);
        statsAdapter = new StatsAdapter("Adn");
        statsAdapter.dynamoDBMapper = dynamoDBMapper;
    }

    @Test
    void stats() {
        var response = statsAdapter.callService(null);
        Assertions.assertEquals(0, response.getData().getCountHuman());
        Assertions.assertEquals(0, response.getData().getRatio());
        Assertions.assertEquals(0, response.getData().getCountMutant());
    }


}