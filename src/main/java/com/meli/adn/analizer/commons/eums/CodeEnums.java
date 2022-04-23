package com.meli.adn.analizer.commons.eums;

public enum CodeEnums {

    SUCCESS("200"),
    INVALID("403"),
    SERVER_ERROR("501");

    private String code;

    CodeEnums(String code) {
        this.code = code;

    }

    public String getCode() {
        return code;
    }
    
}
