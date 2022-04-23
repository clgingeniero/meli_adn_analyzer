package com.meli.adn.analizer.command;

import com.meli.adn.analizer.commons.Request;
import com.meli.adn.analizer.engine.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MutantReqCommand extends Command<Request<String[]>>{

    private Request<String[]> request;
}
