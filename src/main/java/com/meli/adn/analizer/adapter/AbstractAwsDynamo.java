package com.meli.adn.analizer.adapter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.meli.adn.analizer.config.StageNameTableNameResolver;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAwsDynamo {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;
    private final String table;

	protected AbstractAwsDynamo(String tableName) {
        this.table = tableName;
	}

    public <T> Integer count(Class<T> type, DynamoDBScanExpression dbScanExpression) {
        return dynamoDBMapper.count(type, dbScanExpression, DynamoDBMapperConfig.builder().withTableNameResolver(new StageNameTableNameResolver(table)).build());
    }

    public <T> void save(T object) {
        dynamoDBMapper.save(object, DynamoDBMapperConfig.builder().withTableNameResolver(new StageNameTableNameResolver(table)).build());
    }

    public <T> T load(T object) {
        return dynamoDBMapper.load(object, DynamoDBMapperConfig.builder().withTableNameResolver(new StageNameTableNameResolver(table)).build());
    }
}
