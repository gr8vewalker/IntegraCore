package dev.integra.data.impl;

import dev.integra.api.data.IQuery;

import java.util.UUID;

/**
 * Built-in implementation of {@link IQuery} for {@link UUID}
 *
 * @author milizm
 * @since 1.0.0
 */
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
