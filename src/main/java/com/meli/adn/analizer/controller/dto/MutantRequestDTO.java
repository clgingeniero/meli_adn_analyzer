package com.meli.adn.analizer.controller.dto;

import com.meli.adn.analizer.commons.Body;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MutantRequestDTO extends Body {

	private static final long serialVersionUID = 1L;
	private String[] dna;
}
