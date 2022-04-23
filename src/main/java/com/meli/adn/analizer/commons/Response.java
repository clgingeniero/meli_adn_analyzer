package com.meli.adn.analizer.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class Response<R extends Body> implements Serializable {
	/**
	 * 
	 */
    private R data;
    private Status status;
	private static final long serialVersionUID = -2401841328506336253L;
}
