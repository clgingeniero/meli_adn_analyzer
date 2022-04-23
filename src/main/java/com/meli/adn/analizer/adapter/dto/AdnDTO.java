package com.meli.adn.analizer.adapter.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
@Builder
@Getter

public class AdnDTO implements Serializable{

    private String[] adnEval;
    private Boolean isMutant;
}
