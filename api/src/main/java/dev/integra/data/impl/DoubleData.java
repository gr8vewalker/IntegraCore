package dev.integra.data.impl;

import dev.integra.api.data.IData;

public class DoubleData implements IData {

    private final double data;

    public DoubleData(double data) {
        this.data = data;
    }

    @Override
    public Class<?> getType() {
        return Double.class;
    }

    @Override
    public Object getData() {
        return this.data;
    }
}
