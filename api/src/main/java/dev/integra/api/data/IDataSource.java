package dev.integra.api.data;

import java.util.Optional;

public interface IDataSource {

    boolean load();

    boolean save();

    Optional<IData> query(IQuery query);

    boolean insertOrUpdate(IQuery query, IData data);

}
