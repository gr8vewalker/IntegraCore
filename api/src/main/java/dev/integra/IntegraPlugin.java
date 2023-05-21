package dev.integra;

import dev.integra.api.IntegraAPI;
import dev.integra.api.data.IDataSource;
import dev.integra.api.data.serialization.IDataSerializer;
import dev.integra.api.data.serialization.IQuerySerializer;
import dev.integra.command.IntegraCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Logger;

/**
 * Main abstract plugin class for API implementations.
 *
 * @author milizm
 * @see JavaPlugin
 * @see IntegraAPI
 * @since 1.0.0
 */
public abstract class IntegraPlugin extends JavaPlugin {

    protected final Logger LOGGER = getLogger();
    protected List<IDataSource> dataSources;
    protected List<Listener> eventListeners;
    protected List<IntegraCommand> commands;

    @Override
    public void onEnable() {
        LOGGER.info("Using IntegraAPI: " + IntegraAPI.getVersion());
        IntegraAPI.initialize();
        LOGGER.info("Integra: Enabling: " + getDescription().getName() + " v" + getDescription().getVersion());
        getDataFolder().mkdirs();

        init();

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

        commands = registerCommands();
        commands.forEach(IntegraCommand::load);

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

    /**
     * Gets the list of {@link IDataSource} instances for registering, loading, and saving.
     *
     * @return List of {@link IDataSource} instances to register from the plugin
     */
    @NotNull
    protected abstract List<IDataSource> registerDataSources();

    /**
     * @return Interval for BukkitTask that saves all {@link IDataSource} instances
     */
    protected abstract int getDataSourceSaveInterval();

    /**
     * Gets the list of {@link Listener} instances for registration.
     *
     * @return List of {@link Listener} instances to register from the plugin
     */
    @NotNull
    protected abstract List<Listener> registerEventListeners();

    /**
     * Gets the list of {@link IntegraCommand} instances for registration.
     *
     * @return List of {@link IntegraCommand} instances to register from the plugin
     */
    @NotNull
    protected abstract List<IntegraCommand> registerCommands();

    /**
     * Invokes before registering {@link IDataSource}, {@link Listener}, and {@link IntegraCommand} instances.<br>
     * Has the ability to register your data serializers, etc.
     *
     * @see dev.integra.data.serialization.DataRegistration#register(IDataSerializer)
     * @see dev.integra.data.serialization.QueryRegistration#register(IQuerySerializer)
     */
    protected void init() {

    }

    /**
     * Invokes after registering {@link IDataSource}, {@link Listener}, and {@link IntegraCommand} instances when the plugin is enabled.<br>
     * Can be used for registering tasks, placeholders, etc.
     */
    protected abstract void enable();

    /**
     * Invokes after disabling {@link IDataSource} instances when the plugin is disabled.<br>
     * Can be used for unregistering tasks, placeholders, etc.
     */
    protected abstract void disable();

}
