package com.meli.adn.analizer.engine;
import com.meli.adn.analizer.engine.command.Command;
import com.meli.adn.analizer.engine.command.ICommandHandler;
import com.meli.adn.analizer.commons.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import java.util.HashMap;
import java.util.Objects;

public class Registry {

    private static final Logger LOG = LoggerFactory.getLogger(Registry.class);

    private HashMap<Class<? extends Command>, CommandProvider> providerMap = new HashMap<>();

    private ApplicationContext applicationContext;

    @Autowired
    public Registry(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;
        String[] names = applicationContext.getBeanNamesForType(ICommandHandler.class);
        for (String name : names) {
            register(applicationContext, name);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void register(ApplicationContext applicationContext, String name) {
        LOG.info("Iniciando el registro del commando: {} ", name);
        Class<ICommandHandler<?, ?>> handlerClass = (Class<ICommandHandler<?, ?>>) applicationContext.getType(name);
        Class<? extends Command<?>>[] generics = (Class<? extends Command<?>>[]) GenericTypeResolver.resolveTypeArguments(handlerClass, ICommandHandler.class);
        
		Class<? extends Command> commandType =  this.getCommandType(generics);
        providerMap.put(commandType, new CommandProvider(handlerClass));
        LOG.info("Finalizando el registro del commando: {} ", name);
    }

    @SuppressWarnings("unchecked")
    public <R extends Body, C extends Command<R>> ICommandHandler<R, C> get(Class<C> commandClass) {
    	return providerMap.get(commandClass).get(this.applicationContext);
    }

    @SuppressWarnings("unchecked")
	private Class<? extends Command> getCommandType(Class<? extends Command<?>>[] generics) {
    	return (!Objects.isNull(generics)) ? generics[1] : Command.class;
    }
}