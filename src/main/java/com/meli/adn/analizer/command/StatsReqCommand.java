package com.meli.adn.analizer.command;

import com.meli.adn.analizer.commons.Request;
import com.meli.adn.analizer.engine.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.io.Serializable;

@Getter
@AllArgsConstructor
public class StatsReqCommand implements Command<Request<Serializable>>{}
