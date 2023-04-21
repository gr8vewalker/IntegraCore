package dev.integra.data;

import dev.integra.api.IntegraAPI;
import dev.integra.api.data.IData;
import dev.integra.api.data.IQuery;
import dev.integra.api.data.serialization.IQuerySerializer;
import dev.integra.data.impl.*;
import dev.integra.data.serialization.DataRegistration;
import dev.integra.data.serialization.QueryRegistration;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.UUID;

public class SerializationTests {

    @Test
    public void testSerialization() {
        IntegraAPI.initialize();

        testDataSerialization(new StringData("Hello World!"));
        testDataSerialization(new MapData(new HashMap<String, String>() {{
            put("Hello", "World");
        }}));
        testDataSerialization(new IntData(5));
        testDataSerialization(new DoubleData(5.5));

        testQuerySerialization(new StringQuery("Hello World!"));
        testQuerySerialization(new UUIDQuery(UUID.randomUUID()));
    }

    private void testDataSerialization(IData data) {
        Object serialized = DataRegistration.getSerializer(data).serialize(data);
        IData deserialized = DataRegistration.getSerializer(serialized.getClass()).deserialize(serialized);
        Assert.assertEquals(data.getData(), deserialized.getData());
    }

    private void testQuerySerialization(IQuery query) {
        IQuerySerializer serializer = QueryRegistration.getSerializer(query);
        String serialized = serializer.serialize(query);
        IQuery deserialized = serializer.deserialize(serialized);
        Assert.assertEquals(query.getQuery(), deserialized.getQuery());
    }


}
