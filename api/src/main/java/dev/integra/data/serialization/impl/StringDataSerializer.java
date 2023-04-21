package dev.integra.data.serialization.impl;

import dev.integra.api.data.IData;
import dev.integra.api.data.serialization.IDataSerializer;
import dev.integra.data.impl.StringData;
import dev.integra.data.serialization.DataRegistration;

public class StringDataSerializer implements IDataSerializer {

    @Override
    public Object serialize(IData object) {
        return object.getData();
    }

    @Override
    public IData deserialize(Object serialized) {
        String str = (String) serialized;
        if (str.contains("^")) { // If the string contains a ^, it's a serialized object
            // e.g. java.util.Map^<base64data>
            String[] split = str.split("\\^");
            if (split.length == 2) {
                try {
                    IDataSerializer serializer = DataRegistration.getSerializer(Class.forName(split[0]));
                    if (serializer != null) {
                        return serializer.deserialize(split[1]);
                    }
                } catch (ClassNotFoundException ignored) {
                }
            }
        }
        return new StringData(str);
    }

    @Override
    public Class<?> getType() {
        return String.class;
    }
}
