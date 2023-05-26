package dev.integra.api;

import dev.integra.data.serialization.DataRegistration;
import dev.integra.data.serialization.QueryRegistration;
import dev.integra.data.serialization.impl.*;

import java.util.logging.Logger;

/**
 * Main class of IntegraAPI
 *
 * @author milizm
 * @see DataRegistration
 * @see QueryRegistration
 * @see IntDataSerializer
 * @see DoubleDataSerializer
 * @see MapDataSerializer
 * @see StringDataSerializer
 * @see StringQuerySerializer
 * @see UUIDQuerySerializer
 * @see dev.integra.utils.JsonUtils
 * @see dev.integra.data.BasicDataSource
 * @since 1.0.0
 */
public class IntegraAPI {

    private static final Logger LOGGER = Logger.getLogger("IntegraAPI");
    private static boolean initialized = false;

    public static void initialize() {
        if (initialized) return; // Don't initialize twice if there is 1+ plugin using IntegraAPI
        initialized = true;
        LOGGER.info("IntegraAPI: Initializing");
        initializeData();
        LOGGER.info("IntegraAPI: Initialization Successful");
    }

    private static void initializeData() {
        LOGGER.info("IntegraAPI: Initializing Data & Serializer");
        DataRegistration.register(new IntDataSerializer());
        DataRegistration.register(new DoubleDataSerializer());
        DataRegistration.register(new MapDataSerializer());
        DataRegistration.register(new StringDataSerializer());
        QueryRegistration.register(new StringQuerySerializer());
        QueryRegistration.register(new UUIDQuerySerializer());
    }

    public static String getVersion() {
        return "1.0.0";
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
