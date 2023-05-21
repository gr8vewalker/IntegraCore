package dev.integra.api.data.serialization;

import dev.integra.api.data.IData;

/**
 * Serializer for {@link IData}
 *
 * @author milizm
 * @since 1.0.0
 */
public interface IDataSerializer {

    /**
     * Serialize {@link IData} to object
     *
     * @param object {@link IData} to serialize
     * @return Serialized object
     */
    Object serialize(IData object);

    /**
     * Deserialize object to {@link IData}
     *
     * @param serialized Serialized object
     * @return {@link IData} instance
     */
    IData deserialize(Object serialized);

    /**
     * Get type of {@link IData} object
     *
     * @return Type of {@link IData} object
     */
    Class<?> getType();

}
