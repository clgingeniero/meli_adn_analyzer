package com.meli.adn.analizer.controller;
import com.meli.adn.analizer.command.MutantReqCommand;
import com.meli.adn.analizer.command.StatsReqCommand;
import com.meli.adn.analizer.commons.Request;
import com.meli.adn.analizer.controller.dto.MutantResponseDTO;
import com.meli.adn.analizer.controller.dto.StatsResponseDTO;
import com.meli.adn.analizer.engine.command.ICommandBus;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.meli.adn.analizer.commons.Response;

@RestController
public class AdnController {
	
	    @Autowired
	    private ICommandBus commandBus;
		
	   @PostMapping(value = "/mutant", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Response<MutantResponseDTO>> mutantAnalyzer(
				@RequestBody @NonNull Request<String[]> request) {
		   Response<MutantResponseDTO> response = commandBus.handle(new MutantReqCommand(request));
		   return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus().getCode()));
		}

		@GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<StatsResponseDTO> getStats() {
		   Response<StatsResponseDTO> response = commandBus.handle(new StatsReqCommand());
		   return new ResponseEntity<>(response.getData(), HttpStatus.OK);
		}
}

