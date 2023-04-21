package dev.integra.api.data.serialization;

import dev.integra.api.data.IQuery;

public interface IQuerySerializer {

    String serialize(IQuery object);

    IQuery deserialize(String serialized);

    Class<?> getType();

}
