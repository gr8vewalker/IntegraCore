package dev.integra.api;

import dev.integra.data.serialization.DataRegistration;
import dev.integra.data.serialization.QueryRegistration;
import dev.integra.data.serialization.impl.DoubleDataSerializer;
import dev.integra.data.serialization.impl.IntDataSerializer;
import dev.integra.data.serialization.impl.StringQuerySerializer;
import dev.integra.data.serialization.impl.UUIDQuerySerializer;

import java.util.logging.Logger;

public class IntegraAPI {

    private static final Logger LOGGER = Logger.getLogger("IntegraAPI");

    public static void initialize() {
        LOGGER.info("IntegraAPI: Initializing");
        initializeData();
        LOGGER.info("IntegraAPI: Initialization Successful");
    }

    private static void initializeData() {
        LOGGER.info("IntegraAPI: Initializing Data & Serializer");
        DataRegistration.register(new IntDataSerializer());
        DataRegistration.register(new DoubleDataSerializer());
        QueryRegistration.register(new StringQuerySerializer());
        QueryRegistration.register(new UUIDQuerySerializer());
    }

    public static String getVersion() {
        return "1.0.0";
    }

}
