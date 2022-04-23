package com.meli.adn.analizer.engine.command;
import com.meli.adn.analizer.commons.Body;
import com.meli.adn.analizer.commons.Response;

import java.io.Serializable;

/**
 * Manejador del comando
 * @param <R>
 * @param <T>
 */
public interface ICommandHandler<R extends Body, T extends Command<?>> {

    Response<R> handle(T command);
}
