package com.meli.adn.analizer.engine;
import com.meli.adn.analizer.engine.command.Command;
import com.meli.adn.analizer.engine.command.ICommandBus;
import com.meli.adn.analizer.engine.command.ICommandHandler;
import com.meli.adn.analizer.commons.Body;
import com.meli.adn.analizer.commons.Response;

import org.springframework.beans.factory.annotation.Autowired;

public class CommandBus implements ICommandBus {

    private final Registry registry;

    @Autowired
    public CommandBus(Registry registry) {
        this.registry = registry;
    }

    @Override
    public <R extends Body, C extends Command<?>> Response<R> handle(C command) {
        ICommandHandler<R, C> commandHandler = registry.get(command.getClass());
        return commandHandler.handle(command);
    }
}
