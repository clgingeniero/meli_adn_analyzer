package com.meli.adn.analizer.adapter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.meli.adn.analizer.adapter.interfaces.AbstractAwsDynamo;
import com.meli.adn.analizer.adapter.interfaces.IAdapter;
import com.meli.adn.analizer.adapter.model.Adn;
import com.meli.adn.analizer.commons.Request;
import com.meli.adn.analizer.commons.Response;
import com.meli.adn.analizer.controller.dto.StatsResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("StatsAdapter")
public class StatsAdapter extends AbstractAwsDynamo implements IAdapter<Response<StatsResponseDTO>, Request<Serializable>> {

    @Value("${meli.adn.mutant}")
    private String mutant;

    @Value("${meli.adn.human.id}")
    public String humanId;

    @Value("${meli.adn.mutant.id}")
    public String mutantId;

    public StatsAdapter(@Value("${meli.adn.table}") String adnTable) {
		super(adnTable);
	}

    @Override
    public Response<StatsResponseDTO> callService(Request<Serializable> request) {
        int humans = count(Adn.class, getDynamoDBScanExpression(humanId));
        int mutants = count(Adn.class, getDynamoDBScanExpression(mutantId));
        return Response.<StatsResponseDTO>builder()
                .data(StatsResponseDTO.builder()
                        .countHuman(humans)
                        .countMutant(mutants)
                        .ratio((humans >0) ? (double) mutants/humans : mutants)
                        .build()
                )
                .build();
    }

    private DynamoDBScanExpression getDynamoDBScanExpression(String value) {
        DynamoDBScanExpression condition = new DynamoDBScanExpression();
        condition.addFilterCondition(mutant, new Condition().withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withN(value)));
        return condition;
    }
}
