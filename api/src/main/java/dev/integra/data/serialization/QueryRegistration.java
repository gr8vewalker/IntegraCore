package dev.integra.data.serialization;

import dev.integra.api.data.IQuery;
import dev.integra.api.data.serialization.IQuerySerializer;

import java.util.ArrayList;

/**
 * This class is used to register serializers for queries.
 *
 * @author milizm
 * @since 1.0.0
 */
public class QueryRegistration {

    private static final ArrayList<IQuerySerializer> serializers = new ArrayList<>();

    public static void register(IQuerySerializer serializer) {
        serializers.add(serializer);
    }

    public static ArrayList<IQuerySerializer> getSerializers() {
        return serializers;
    }

    public static IQuerySerializer getSerializer(IQuery query) {
        return getSerializer(query.getType());
    }

    public static IQuerySerializer getSerializer(Class<?> type) {
        return serializers.stream().filter(c -> c.getType() == type).findFirst().orElse(serializers.stream().filter(c -> c.getType().isAssignableFrom(type)).findFirst().orElse(null));
    }

}
