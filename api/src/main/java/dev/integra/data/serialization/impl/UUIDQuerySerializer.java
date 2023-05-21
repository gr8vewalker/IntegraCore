package dev.integra.data.serialization.impl;

import dev.integra.api.data.IQuery;
import dev.integra.api.data.serialization.IQuerySerializer;
import dev.integra.data.impl.UUIDQuery;

import java.util.UUID;

/**
 * Built-in serializer for {@link UUIDQuery}
 *
 * @author milizm
 * @since 1.0.0
 */
public class UUIDQuerySerializer implements IQuerySerializer {
    @Override
    public String serialize(IQuery object) {
        return object.getQuery().toString();
    }

    @Override
    public IQuery deserialize(String serialized) {
        return new UUIDQuery(UUID.fromString(serialized));
    }

    @Override
    public Class<?> getType() {
        return UUID.class;
    }
}
