package com.meli.adn.analizer.commons;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Status implements Serializable{

    private Integer code;
    private String description;
}
