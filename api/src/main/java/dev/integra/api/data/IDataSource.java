package dev.integra.api.data;

import java.util.Optional;

/**
 * Data source interface
 *
 * @author milizm
 * @see dev.integra.data.BasicDataSource
 * @since 1.0.0
 */
public interface IDataSource {

    /**
     * Load data from source
     *
     * @return true if success
     */
    boolean load();

    /**
     * Save data to source
     *
     * @return true if success
     */
    boolean save();

    /**
     * Query data from source
     *
     * @param query {@link IQuery} object
     * @return Optional of {@link IData} includes data if found
     */
    Optional<IData> query(IQuery query);

    /**
     * Query data from source
     *
     * @since 1.0.2
     * @param query {@link IQuery} object
     * @return Optional of {@link IData} includes data if found
     * @param <T> Type of {@link IData}
     */
    <T extends IData> Optional<T> queryTyped(IQuery query);

    /**
     * Insert or update data to source
     *
     * @param query {@link IQuery} object
     * @param data  {@link IData} object
     * @return true if success
     */
    boolean insertOrUpdate(IQuery query, IData data);

}
