package com.meli.adn.analizer.command.handler;

import com.meli.adn.analizer.adapter.interfaces.IAdapter;
import com.meli.adn.analizer.command.StatsReqCommand;
import com.meli.adn.analizer.commons.Request;
import com.meli.adn.analizer.commons.Response;
import com.meli.adn.analizer.controller.dto.StatsResponseDTO;
import com.meli.adn.analizer.engine.command.ICommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class StatsCommand implements ICommandHandler<StatsResponseDTO, StatsReqCommand> {

	@Autowired
    @Qualifier("StatsAdapter")
    private IAdapter<Response<StatsResponseDTO>, Request<Serializable>> statsAdapter;

	@Override
	public Response<StatsResponseDTO> handle(StatsReqCommand command) {
		return statsAdapter.callService(null);
	}
}

