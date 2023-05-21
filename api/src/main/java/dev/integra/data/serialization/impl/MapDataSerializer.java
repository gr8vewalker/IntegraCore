package dev.integra.data.serialization.impl;

import dev.integra.api.data.IData;
import dev.integra.api.data.serialization.IDataSerializer;
import dev.integra.data.impl.MapData;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Built-in serializer for {@link MapData}.<br><br>
 * <p>
 * Serializes data into a string with the following format:
 * <code>java.util.Map^base64encoded</code><br><br>
 * By adding type information to the beginning of the string, we avoid conflicts with the String deserializer.
 * </p>
 *
 * @author milizm
 * @see ObjectOutputStream serialization of Map
 * @see ObjectInputStream deserialization of Map
 * @see Base64 encoding and decoding for writability
 * @since 1.0.0
 */
public class MapDataSerializer implements IDataSerializer {
    @Override
    public Object serialize(IData object) {
        Map<?, ?> data = (Map<?, ?>) object.getData();
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream serialized = new ObjectOutputStream(bytes);
            for (Map.Entry<?, ?> entry : data.entrySet()) {
                serialized.writeObject(entry.getKey());
                serialized.writeObject(entry.getValue());
            }
            serialized.close();
            String encoded = new String(Base64.getEncoder().encode(bytes.toByteArray()), StandardCharsets.UTF_8);
            return getType().getName() + "^" + encoded;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public IData deserialize(Object serialized) {
        byte[] bytes = Base64.getDecoder().decode(((String) serialized).getBytes());
        Map<Object, Object> data = new HashMap<>();
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            ObjectInputStream deserialized = new ObjectInputStream(stream);
            while (stream.available() > 0) {
                Object key = deserialized.readObject();
                Object value = deserialized.readObject();
                data.put(key, value);
            }
            deserialized.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new MapData(data);
    }

    @Override
    public Class<?> getType() {
        return Map.class;
    }
}
