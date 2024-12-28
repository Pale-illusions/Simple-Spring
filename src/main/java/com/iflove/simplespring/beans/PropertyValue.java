package com.iflove.simplespring.beans;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote bean属性注入
 */

public class PropertyValue {
    private final String name;

    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
