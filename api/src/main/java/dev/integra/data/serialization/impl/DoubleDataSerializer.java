package dev.integra.data.serialization.impl;

import dev.integra.api.data.IData;
import dev.integra.api.data.serialization.IDataSerializer;
import dev.integra.data.impl.DoubleData;

public class DoubleDataSerializer implements IDataSerializer {
    @Override
    public Object serialize(IData object) {
        return object.getData();
    }

    @Override
    public IData deserialize(Object serialized) {
        return new DoubleData((double) serialized);
    }

    @Override
    public Class<?> getType() {
        return Double.class;
    }
}
