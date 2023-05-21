package dev.integra.data.impl;

import dev.integra.api.data.IData;

/**
 * Built-in implementation of {@link IData} for {@link String}
 *
 * @author milizm
 * @since 1.0.0
 */
public class StringData implements IData {

    private final String data;

    public StringData(String data) {
        this.data = data;
    }

    @Override
    public Class<?> getType() {
        return String.class;
    }

    @Override
    public Object getData() {
        return data;
    }
}
