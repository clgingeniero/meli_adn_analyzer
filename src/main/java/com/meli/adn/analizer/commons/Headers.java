package com.meli.adn.analizer.commons;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Headers implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonProperty("Content-Type")
	private String contentType;
}
