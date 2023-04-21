package dev.integra.data.serialization;

import dev.integra.api.data.IData;
import dev.integra.api.data.serialization.IDataSerializer;

import java.util.ArrayList;

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
        for (IDataSerializer serializer : serializers) {
            if (serializer.getType().getName().equals(type.getName())) {
                return serializer;
            }
        }
        return null;
    }

}
