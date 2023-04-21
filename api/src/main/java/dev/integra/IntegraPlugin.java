package dev.integra;

import dev.integra.api.IntegraAPI;
import dev.integra.api.data.IDataSource;
import dev.integra.data.serialization.DataRegistration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Logger;

public abstract class IntegraPlugin extends JavaPlugin {

    protected final Logger LOGGER = getLogger();
    protected List<IDataSource> dataSources;
    protected List<Listener> eventListeners;

    @Override
    public void onEnable() {
        LOGGER.info("Using IntegraAPI: " + IntegraAPI.getVersion());
        IntegraAPI.initialize();
        LOGGER.info("Integra: Enabling: " + getDescription().getName() + " v" + getDescription().getVersion());
        getDataFolder().mkdirs();

        dataSources = registerDataSources();
        dataSources.forEach(source -> {
            if (!source.load()) {
                LOGGER.warning("Integra: Failed to load DataSource instance of " + source.getClass().getSimpleName());
            }
        });
        getServer().getScheduler().runTaskTimerAsynchronously(this, () -> dataSources.forEach(source -> {
            if (!source.save()) {
                LOGGER.warning("Integra: Failed to save DataSource instance of " + source.getClass().getSimpleName());
            }
        }), 0, getDataSourceSaveInterval());

        eventListeners = registerEventListeners();
        eventListeners.forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));


        enable();
    }

    @Override
    public void onDisable() {
        LOGGER.info("Integra: Disabling: " + getDescription().getName() + " v" + getDescription().getVersion());
        dataSources.forEach(source -> {
            if (!source.save()) {
                LOGGER.warning("Integra: Failed to save DataSource instance of " + source.getClass().getSimpleName());
            }
        });

        disable();
    }

    @NotNull
    protected abstract List<IDataSource> registerDataSources();

    protected abstract int getDataSourceSaveInterval();

    @NotNull
    protected abstract List<Listener> registerEventListeners();

    protected abstract void enable();

    protected abstract void disable();

}
