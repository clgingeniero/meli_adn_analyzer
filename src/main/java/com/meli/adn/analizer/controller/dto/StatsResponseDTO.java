package com.meli.adn.analizer.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meli.adn.analizer.commons.Body;
import lombok.*;

@Builder
@Setter
@Getter
public class StatsResponseDTO extends Body{

	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "count_mutant_dna")
	private Integer countMutant;

	@JsonProperty(value = "count_human_dna")
	private Integer countHuman;

	@JsonProperty(value = "ratio")
	private double ratio;


}

