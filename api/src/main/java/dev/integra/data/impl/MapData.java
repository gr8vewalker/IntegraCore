package dev.integra.data.impl;

import dev.integra.api.data.IData;

import java.util.Map;

/**
 * Built-in implementation of {@link IData} for {@link Map}
 *
 * @author milizm
 * @since 1.0.0
 */
public class MapData implements IData {

    private final Map<?, ?> data;

    public MapData(Map<?, ?> data) {
        this.data = data;
    }

    @Override
    public Class<?> getType() {
        return Map.class;
    }

    @Override
    public Object getData() {
        return data;
    }
}
