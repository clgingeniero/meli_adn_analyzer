package com.meli.adn.analizer;

import com.meli.adn.analizer.engine.Registry;
import com.meli.adn.analizer.engine.CommandBus;
import com.meli.adn.analizer.engine.command.ICommandBus;
import com.meli.adn.analizer.engine.decorator.LoggerDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com.meli.adn.analizer"})
public class MeliAdnAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeliAdnAnalyzerApplication.class, args);
	}

	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public ICommandBus commandBus() {
		return new LoggerDecorator(new CommandBus(new Registry(applicationContext)));
	}

}
