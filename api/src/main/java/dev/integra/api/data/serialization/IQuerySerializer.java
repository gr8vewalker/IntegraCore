package dev.integra.api.data.serialization;

import dev.integra.api.data.IQuery;

/**
 * Serializer for {@link IQuery}
 *
 * @author milizm
 * @since 1.0.0
 */
public interface IQuerySerializer {

    /**
     * Serialize {@link IQuery} to string
     *
     * @param object {@link IQuery} to serialize
     * @return Serialized string
     */
    String serialize(IQuery object);

    /**
     * Deserialize string to {@link IQuery}
     *
     * @param serialized Serialized string
     * @return {@link IQuery} instance
     */
    IQuery deserialize(String serialized);

    /**
     * Get type of {@link IQuery} object
     *
     * @return Type of {@link IQuery} object
     */
    Class<?> getType();

}
