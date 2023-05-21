package dev.integra.data.serialization.impl;

import dev.integra.api.data.IData;
import dev.integra.api.data.serialization.IDataSerializer;
import dev.integra.data.impl.StringData;
import dev.integra.data.serialization.DataRegistration;

/**
 * Built-in serializer for {@link StringData}.<br><br>
 *
 * <p>
 * While deserializing, if the string contains a ^, it's a serialized object with type information.<br>
 * e.g. java.util.Map^base64data<br><br>
 * So it splits the string by ^ and gets the deserializer for the type, then deserializes the data.
 * </p>
 *
 * @author milizm
 * @see MapDataSerializer MapDataSerializer for an example of a serialized object with type information
 * @since 1.0.0
 */
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
