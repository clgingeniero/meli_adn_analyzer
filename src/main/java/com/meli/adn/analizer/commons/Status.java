package com.meli.adn.analizer.commons;

import java.io.Serializable;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class Status implements Serializable{

    private Integer code;
    private String description;
}
