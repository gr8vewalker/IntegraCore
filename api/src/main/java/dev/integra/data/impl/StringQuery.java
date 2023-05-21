package dev.integra.data.impl;

import dev.integra.api.data.IQuery;

/**
 * Built-in implementation of {@link IQuery} for {@link String}
 *
 * @author milizm
 * @since 1.0.0
 */
public class StringQuery implements IQuery {

    private final String query;

    public StringQuery(String query) {
        this.query = query;
    }

    @Override
    public Class<?> getType() {
        return String.class;
    }

    @Override
    public Object getQuery() {
        return query;
    }
}
