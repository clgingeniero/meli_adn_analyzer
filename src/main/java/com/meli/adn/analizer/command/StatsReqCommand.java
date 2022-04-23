package com.meli.adn.analizer.command;

import com.meli.adn.analizer.commons.Request;
import com.meli.adn.analizer.engine.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatsReqCommand extends Command<Request<Serializable>>{

    private Request<Serializable> request;
}
