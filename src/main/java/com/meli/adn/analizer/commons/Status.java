package com.meli.adn.analizer.commons;

import java.io.Serializable;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Status implements Serializable{

    private Integer code;
    private String description;
}
