package dev.integra;

import dev.integra.api.IntegraAPI;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class IntegraPlugin extends JavaPlugin {

    protected Logger LOGGER = getLogger();

    @Override
    public void onEnable() {
        LOGGER.info("Using IntegraAPI: " + IntegraAPI.getVersion());
        LOGGER.info("Integra: Enabling: " + getDescription().getName() + " v" + getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        LOGGER.info("Integra: Disabling: " + getDescription().getName() + " v" + getDescription().getVersion());
    }

}
