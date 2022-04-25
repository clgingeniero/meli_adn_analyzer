package com.meli.adn.analizer.commons;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Request <R extends Serializable> {
    private R dna;
    private Headers headers;
}
