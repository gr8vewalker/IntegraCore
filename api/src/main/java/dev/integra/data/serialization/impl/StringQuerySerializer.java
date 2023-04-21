package dev.integra.data.serialization.impl;

import dev.integra.api.data.serialization.IQuerySerializer;

public class StringQuerySerializer implements IQuerySerializer {
    @Override
    public <K> String serialize(K object) {
        return object.toString();
    }

    @Override
    public <K> K deserialize(String serialized) {
        return (K) serialized;
    }

    @Override
    public Class<?> getType() {
        return String.class;
    }
}
