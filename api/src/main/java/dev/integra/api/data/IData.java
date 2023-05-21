package dev.integra.api.data;

/**
 * Data object for storing in data source
 *
 * @author milizm
 * @since 1.0.0
 */
public interface IData {

    /**
     * Creates a new {@link IData} instance
     *
     * @param data Data object
     * @return {@link IData} instance with given data object and its class
     */
    static IData of(Object data) {
        return new IData() {
            @Override
            public Class<?> getType() {
                return data.getClass();
            }

            @Override
            public Object getData() {
                return data;
            }
        };
    }

    /**
     * Get type of data
     *
     * @return Type of data
     */
    Class<?> getType();

    /**
     * Get data object
     *
     * @return the data object
     */
    Object getData();

}
