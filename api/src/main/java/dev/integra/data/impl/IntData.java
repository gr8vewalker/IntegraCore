package dev.integra.data.impl;

import dev.integra.api.data.IData;

public class IntData implements IData {

    private final int data;

    public IntData(int data) {
        this.data = data;
    }

    @Override
    public Class<?> getType() {
        return Integer.class;
    }

    @Override
    public Object getData() {
        return data;
    }
}
