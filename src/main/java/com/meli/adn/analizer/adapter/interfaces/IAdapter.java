package com.meli.adn.analizer.adapter.interfaces;


import com.meli.adn.analizer.commons.Response;

public interface IAdapter<R extends Response, C> {
    R callService(C request);
}
