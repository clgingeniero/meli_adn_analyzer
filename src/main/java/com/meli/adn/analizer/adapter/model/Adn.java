package com.meli.adn.analizer.adapter.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@DynamoDBTable(tableName = "Adn")
public class Adn {

    @DynamoDBHashKey(attributeName = "Dna")
    private String dna;
    
    @DynamoDBAttribute(attributeName = "Mutant")
    private Boolean mutant;
}