package dev.integra;

import com.google.common.collect.Maps;
import dev.integra.api.data.IData;
import dev.integra.api.data.IDataSource;
import dev.integra.command.IntegraCommand;
import dev.integra.command.TestCommand;
import dev.integra.data.file.FileDataSource;
import dev.integra.data.impl.IntData;
import dev.integra.data.impl.MapData;
import dev.integra.data.impl.StringQuery;
import dev.integra.listener.TestListener;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class TestPlugin extends IntegraPlugin {

    private static TestPlugin instance;

    private final File testFile = new File(getDataFolder(), "testyml.yml");
    private final FileDataSource<String> dataSource = new FileDataSource<>(testFile, FileDataSource.FileType.YAML, String.class);

    public static TestPlugin getInstance() {
        return instance;
    }

    @Override
    @NotNull
    public List<IDataSource> registerDataSources() {
        return Collections.singletonList(dataSource);
    }

    @Override
    public int getDataSourceSaveInterval() {
        return 20 * 60 * 5;
    }

    @Override
    protected @NotNull List<Listener> registerEventListeners() {
        return Collections.singletonList(new TestListener());
    }

    @Override
    protected @NotNull List<IntegraCommand> registerCommands() {
        return Collections.singletonList(new TestCommand());
    }

    @Override
    protected void enable() {
        instance = this;
        dataSource.insertOrUpdate(new StringQuery("test"), new IntData(ThreadLocalRandom.current().nextInt()));
        dataSource.insertOrUpdate(new StringQuery("testMap"), new MapData(Maps.asMap(Collections.singleton("test"), s -> ThreadLocalRandom.current().nextInt())));
    }

    @Override
    protected void disable() {
        Optional<IData> queriedData = dataSource.query(new StringQuery("test"));
        queriedData.ifPresent(data -> getLogger().info("Data Retrieved: " + data.getData()));

        Optional<IData> queriedMapData = dataSource.query(new StringQuery("testMap"));
        queriedMapData.ifPresent(data -> getLogger().info("Map Data Retrieved: " + data.getData()));

        Optional<IntData> quiriedIntData = dataSource.queryTyped(new StringQuery("test"));
        quiriedIntData.ifPresent(data -> getLogger().info("Int Data Retrieved: " + data.getData()));
    }
}
