package com.alexei.abianna.domain;

public enum PathBlocksEnum {
    HORIZONTAL_WAY("-"),
    VERTICAL_WAY("|"),
    TURN_LEFT_WAY("/"),
    TURN_RIGHT_WAY("\\");

    private String value;

    PathBlocksEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
