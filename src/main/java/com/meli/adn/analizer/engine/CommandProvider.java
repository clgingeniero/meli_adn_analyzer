package com.meli.adn.analizer.engine;

import com.meli.adn.analizer.engine.command.ICommandHandler;
import org.springframework.context.ApplicationContext;

public class CommandProvider<H extends ICommandHandler<?, ?>> {

    private final Class<H> type;
    CommandProvider(Class<H> type) {
        this.type = type;
    }
    public H get(ApplicationContext applicationContext) {
        return applicationContext.getBean(type);
    }
}