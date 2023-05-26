package dev.integra.data.serialization;

import dev.integra.api.data.IData;
import dev.integra.api.data.serialization.IDataSerializer;

import java.util.ArrayList;

/**
 * This class is used to register serializers for data.
 *
 * @author milizm
 * @since 1.0.0
 */
public class DataRegistration {

    private static final ArrayList<IDataSerializer> serializers = new ArrayList<>();

    public static void register(IDataSerializer serializer) {
        serializers.add(serializer);
    }

    public static ArrayList<IDataSerializer> getSerializers() {
        return serializers;
    }

    public static IDataSerializer getSerializer(IData data) {
        return getSerializer(data.getType());
    }

    public static IDataSerializer getSerializer(Class<?> type) {
        return serializers.stream().filter(c -> c.getType() == type).findFirst().orElse(serializers.stream().filter(c -> c.getType().isAssignableFrom(type)).findFirst().orElse(null));
    }

}
