package dev.integra.api.data;

public interface IQuery {

    Class<?> getType();

    Object getQuery();

    static IQuery of(Class<?> type, Object query) {
        return new IQuery() {
            @Override
            public Class<?> getType() {
                return type;
            }

            @Override
            public Object getQuery() {
                return query;
            }
        };
    }

}
