package com.zac;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.Config;

import java.util.List;
import java.util.Map;

public class ConfigActions {

    public static CommentedFileConfig config;

    public static void init(String configPath, Map<String, Object> defaults, Map<String, String> comments) {
        Config.setInsertionOrderPreserved(true);
        config = CommentedFileConfig.builder(configPath).autosave().build();
        config.load();
        for (Map.Entry<String, Object> entry : defaults.entrySet()) {
            System.out.println(entry);
            if (!config.contains(entry.getKey())) {
                config.set(entry.getKey(), entry.getValue());
            }

            if (comments.containsKey(entry.getKey())) {
                config.setComment(entry.getKey(), comments.get(entry.getKey()));
            }
        }
    }

    public static int readInt(String key) {
        return config.getIntOrElse(key, 0);
    }

    public static boolean readBool(String key) {
        return (boolean) config.get(key);
    }

    @SuppressWarnings("unchecked")
    public static List<String> readList(String key) {
        return (List<String>) config.get(key);
    }

    public static void close() {
        config.close();
    }
}