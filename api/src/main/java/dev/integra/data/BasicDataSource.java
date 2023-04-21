package dev.integra.data;

import dev.integra.api.data.IData;
import dev.integra.api.data.IDataSource;
import dev.integra.api.data.IQuery;
import dev.integra.api.data.serialization.IQuerySerializer;
import dev.integra.data.serialization.QueryRegistration;

import java.util.HashMap;
import java.util.Optional;

public abstract class BasicDataSource<K> implements IDataSource {

    protected final HashMap<K, IData> keyValueMap = new HashMap<>();
    protected final Class<?> keyClass;

    public BasicDataSource(Class<K> keyClass) {
        this.keyClass = keyClass;
    }

    @Override
    public Optional<IData> query(IQuery query) {
        if (query.getType() == getKeyClass()) {
            return Optional.ofNullable(keyValueMap.get(query.getQuery()));
        }
        return Optional.empty();
    }

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

    protected IQuerySerializer getQuerySerializer() {
        return QueryRegistration.getSerializer(getKeyClass());
    }
}
