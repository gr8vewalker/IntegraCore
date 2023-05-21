package dev.integra.data.file;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.integra.api.data.IData;
import dev.integra.api.data.IQuery;
import dev.integra.data.BasicDataSource;
import dev.integra.data.serialization.DataRegistration;
import dev.integra.utils.JsonUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

/**
 * Data source implementation for file with json or yaml format
 *
 * @param <K> Key type
 * @author milizm
 * @since 1.0.0
 */
public class FileDataSource<K> extends BasicDataSource<K> {

    private final File file;
    private final FileType type;

    public FileDataSource(File file, FileType type, Class<K> keyType) {
        super(keyType);
        this.file = file;
        this.type = type;
    }

    public File getFile() {
        return file;
    }

    public FileType getType() {
        return type;
    }

    /**
     * Main method for saving data. Uses {@link #saveJson()} or {@link #saveYaml()} depends on {@link #type}
     *
     * @return true if success
     */
    @Override
    public boolean load() {
        if (!file.exists()) save();
        return this.type == FileType.JSON ? loadJson() : loadYaml();
    }

    /**
     * Loads data from json file
     *
     * @return true if success
     */
    private boolean loadJson() {
        try {
            JsonObject object = new JsonParser().parse(Files.newBufferedReader(file.toPath())).getAsJsonObject();
            Map<String, Object> map = JsonUtils.toMap(object.toString());
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                K key = (K) getQuerySerializer().deserialize(entry.getKey()).getQuery();
                IData value = DataRegistration.getSerializer(entry.getValue().getClass()).deserialize(entry.getValue());
                keyValueMap.put(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Loads data from yaml file
     *
     * @return true if success
     */
    private boolean loadYaml() {
        try {
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            for (String key : configuration.getKeys(false)) {
                K k = (K) getQuerySerializer().deserialize(key).getQuery();
                Object value = configuration.get(key);
                IData v = DataRegistration.getSerializer(value.getClass()).deserialize(value);
                keyValueMap.put(k, v);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Main save method. Uses {@link #saveJson()} or {@link #saveYaml()} depends on {@link #type}
     *
     * @return true if success
     */
    @Override
    public boolean save() {
        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (!created) {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return this.type == FileType.JSON ? saveJson() : saveYaml();
    }

    /**
     * Saves data to json file
     *
     * @return true if success
     */
    private boolean saveJson() {
        try {
            JsonObject object = new JsonObject();
            for (Map.Entry<K, IData> entry : keyValueMap.entrySet()) {
                String key = getQuerySerializer().serialize(IQuery.of(entry.getKey()));
                Object serialized = DataRegistration.getSerializer(entry.getValue()).serialize(entry.getValue());
                JsonElement element = JsonUtils.toJsonElement(serialized);
                object.add(key, element);
            }
            Files.write(file.toPath(), object.toString().getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Saves data to yaml file
     *
     * @return true if success
     */
    private boolean saveYaml() {
        try {
            YamlConfiguration configuration = new YamlConfiguration();
            for (Map.Entry<K, IData> entry : keyValueMap.entrySet()) {
                String key = getQuerySerializer().serialize(IQuery.of(entry.getKey()));
                Object serialized = DataRegistration.getSerializer(entry.getValue()).serialize(entry.getValue());
                configuration.set(key, serialized);
            }
            configuration.save(file);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * File type for loading and saving
     */
    public enum FileType {
        JSON,
        YAML
    }
}
