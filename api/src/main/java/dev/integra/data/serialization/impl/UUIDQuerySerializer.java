package dev.integra.data.serialization.impl;

import dev.integra.api.data.serialization.IQuerySerializer;

import java.util.UUID;

public class UUIDQuerySerializer implements IQuerySerializer {
    @Override
    public <K> String serialize(K object) {
        return object.toString();
    }

    @Override
    public <K> K deserialize(String serialized) {
        return (K) UUID.fromString(serialized);
    }

    @Override
    public Class<?> getType() {
        return UUID.class;
    }
}
