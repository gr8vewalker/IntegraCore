package dev.integra.api.data.serialization;

public interface IQuerySerializer {

    <K> String serialize(K object);

    <K> K deserialize(String serialized);

    Class<?> getType();

}
