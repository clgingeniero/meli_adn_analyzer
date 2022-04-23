package com.meli.adn.analizer.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meli.adn.analizer.commons.Body;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MutantResponseDTO extends Body{

	private static final long serialVersionUID = 1L;
	@JsonProperty(value = "is_mutant")
	private Boolean isMutant;

}

