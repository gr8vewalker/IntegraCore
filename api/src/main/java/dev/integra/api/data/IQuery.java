package dev.integra.api.data;

/**
 * Key of data source for finding data
 *
 * @author milizm
 * @since 1.0.0
 */
public interface IQuery {

    /**
     * Creates a new {@link IQuery} instance
     *
     * @param query Query object
     * @return {@link IQuery} instance with given query object and its class
     */
    static IQuery of(Object query) {
        return new IQuery() {
            @Override
            public Class<?> getType() {
                return query.getClass();
            }

            @Override
            public Object getQuery() {
                return query;
            }
        };
    }

    /**
     * Get query object class
     *
     * @return Query object class
     */
    Class<?> getType();

    /**
     * Get query object
     *
     * @return Query object
     */
    Object getQuery();

}
