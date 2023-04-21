package dev.integra.data.serialization;

import dev.integra.api.data.IQuery;
import dev.integra.api.data.serialization.IQuerySerializer;

import java.util.ArrayList;

public class QueryRegistration {

    private static final ArrayList<IQuerySerializer> serializers = new ArrayList<>();

    public static void register(IQuerySerializer serializer) {
        serializers.add(serializer);
    }

    public static ArrayList<IQuerySerializer> getSerializers() {
        return serializers;
    }

    public static IQuerySerializer getSerializer(IQuery query) {
        return getSerializer(query.getClass());
    }

    public static IQuerySerializer getSerializer(Class<?> type) {
        for (IQuerySerializer serializer : serializers) {
            if (serializer.getType().getName().equals(type.getName())) {
                return serializer;
            }
        }
        return null;
    }

}
