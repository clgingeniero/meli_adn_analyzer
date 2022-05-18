package com.meli.adn.analizer.engine.command;
import com.meli.adn.analizer.commons.Body;
import com.meli.adn.analizer.commons.Response;

public interface ICommandBus {

    <R extends Body, C extends Command<?>> Response<R> handle(C command);
}
