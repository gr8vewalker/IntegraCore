package dev.integra.data.impl;

import dev.integra.api.data.IQuery;

import java.util.UUID;

public class UUIDQuery implements IQuery {

    private final UUID uuid;

    public UUIDQuery(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public Class<?> getType() {
        return UUID.class;
    }

    @Override
    public Object getQuery() {
        return uuid;
    }
}
