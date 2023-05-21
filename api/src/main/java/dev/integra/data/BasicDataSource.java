package dev.integra.data;

import dev.integra.api.data.IData;
import dev.integra.api.data.IDataSource;
import dev.integra.api.data.IQuery;
import dev.integra.api.data.serialization.IQuerySerializer;
import dev.integra.data.serialization.QueryRegistration;

import java.util.HashMap;
import java.util.Optional;

/**
 * Key-value data source
 *
 * @param <K> Key type
 * @author milizm
 * @since 1.0.0
 */
public abstract class BasicDataSource<K> implements IDataSource {

    protected final HashMap<K, IData> keyValueMap = new HashMap<>(); // TODO: use fastutil or faster implementations
    protected final Class<?> keyClass;

    public BasicDataSource(Class<K> keyClass) {
        this.keyClass = keyClass;
    }

    /**
     * Getting data from key-value map
     *
     * @param query Key for finding data
     * @return Optional of IData includes data if found
     */
    @Override
    public Optional<IData> query(IQuery query) {
        if (query.getType() == getKeyClass()) {
            return Optional.ofNullable(keyValueMap.get(query.getQuery()));
        }
        return Optional.empty();
    }

    /**
     * Insert or update data to key-value map with specific key
     *
     * @param query Key for finding data
     * @param data  Data to insert or update
     * @return true if success
     */
    @Override
    public boolean insertOrUpdate(IQuery query, IData data) {
        if (query.getType() == getKeyClass()) {
            keyValueMap.put((K) query.getQuery(), data);
            return true;
        }
        return false;
    }

    protected Class<?> getKeyClass() {
        return keyClass;
    }

    /**
     * @return {@link IQuerySerializer} for this data sources key type
     */
    protected IQuerySerializer getQuerySerializer() {
        return QueryRegistration.getSerializer(getKeyClass());
    }
}
