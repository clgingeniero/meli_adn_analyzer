package com.meli.adn.analizer.engine.decorator;
import com.meli.adn.analizer.engine.command.ICommandBus;
import com.meli.adn.analizer.commons.Body;
import com.meli.adn.analizer.commons.Response;
import com.meli.adn.analizer.engine.command.Command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerDecorator implements ICommandBus {

    private ICommandBus decorated;
    private static final Logger LOG = LoggerFactory.getLogger(LoggerDecorator.class);


    public LoggerDecorator(ICommandBus decorated) {
        this.decorated = decorated;
    }

    @Override
    public <R extends Body, C extends Command<?>> Response<R> handle(C command) {
        LOG.info("--- Before - Decorator {}", command.getClass().getSimpleName());
        Response<R> response = decorated.handle(command);
        LOG.info("--- After decorator {}", command.getClass().getSimpleName());
        return response;
    }
}
