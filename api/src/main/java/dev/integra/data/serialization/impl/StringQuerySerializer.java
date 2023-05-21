package dev.integra.data.serialization.impl;

import dev.integra.api.data.IQuery;
import dev.integra.api.data.serialization.IQuerySerializer;
import dev.integra.data.impl.StringQuery;

/**
 * Built-in serializer for {@link StringQuery}
 *
 * @author milizm
 * @since 1.0.0
 */
public class StringQuerySerializer implements IQuerySerializer {
    @Override
    public String serialize(IQuery object) {
        return (String) object.getQuery();
    }

    @Override
    public IQuery deserialize(String serialized) {
        return new StringQuery(serialized);
    }

    @Override
    public Class<?> getType() {
        return String.class;
    }
}
