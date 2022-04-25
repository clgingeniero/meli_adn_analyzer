package com.meli.adn.analizer.config;

import org.springframework.beans.factory.annotation.Autowired;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

public class StageNameTableNameResolver extends DynamoDBMapperConfig.DefaultTableNameResolver {
	
	private final String tableName;

	@Autowired
	public StageNameTableNameResolver(String tableName) {
		this.tableName = tableName;
	}
	
	 @Override
	 public String getTableName(Class<?> clazz, DynamoDBMapperConfig config) {
		return this.tableName;
	 }
}