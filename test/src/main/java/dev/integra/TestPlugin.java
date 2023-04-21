package dev.integra;

import dev.integra.api.data.IData;
import dev.integra.api.data.IDataSource;
import dev.integra.api.data.IQuery;
import dev.integra.data.file.FileDataSource;
import dev.integra.data.impl.IntData;
import dev.integra.data.impl.StringQuery;
import dev.integra.listener.TestListener;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TestPlugin extends IntegraPlugin {

    private static TestPlugin instance;

    private final File testFile = new File(getDataFolder(), "test.json");
    private final FileDataSource<String> dataSource = new FileDataSource<>(testFile, FileDataSource.FileType.JSON, String.class);

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
    protected void enable() {
        instance = this;
        dataSource.insertOrUpdate(new StringQuery("test"), new IntData(ThreadLocalRandom.current().nextInt()));
    }

    @Override
    protected void disable() {
        Optional<IData> queryedData = dataSource.query(new StringQuery("test"));
        queryedData.ifPresent(data -> getLogger().info("Data Retrieved: " + data.getData()));
    }

    public static TestPlugin getInstance() {
        return instance;
    }
}