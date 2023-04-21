package dev.integra.api.data;

public interface IQuery {

    Class<?> getType();

    Object getQuery();

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

}
