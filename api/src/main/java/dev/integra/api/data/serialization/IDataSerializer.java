package dev.integra.api.data.serialization;

import dev.integra.api.data.IData;

public interface IDataSerializer {

    Object serialize(IData object);

    IData deserialize(Object serialized);

    Class<?> getType();

}
