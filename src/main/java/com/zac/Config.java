// very cool abstraction layer

package com.zac;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.MinecraftServer;

public class Config {

    public static int entityMultiplierRate;
    public static List<String> entityMultiplierEntities;
    public static List<String> entityBlocklistEntities;
    public static List<String> daylightResistantEntities;
    public static List<String> additionalZombieDrops;
    public static boolean heavilyArmoredZombiesEnabled;
    public static boolean dayCounterEnabled;

    private static final Map<String, Object> defaults = new LinkedHashMap<>() {
        {
            put("entityMultiplierRate", 1);
            put("entityMultiplierEntities", List.of());
            put("entityBlocklistEntities", List.of());
            put("daylightResistantEntities", List.of("minecraft:zombie"));
            put("additionalZombieDrops",
                    List.of("minecraft:rotten_flesh=0.9", "minecraft:bone=0.1", "minecraft:carrot=0.009",
                            "minecraft:potato=0.0009", "minecraft:iron_nugget=0.0005", "minecraft:gold_nugget=0.0001"));
            put("heavilyArmoredZombiesEnabled", false);
            put("dayCounterEnabled", true);
        }
    };

    private static final Map<String, String> comments = new LinkedHashMap<>() {
        {
            put("entityMultiplierRate", "PLEASE READ THE INSTRUCTIONS ON THE MOD PAGE TO PREVENT MISTAKES!\n---");
            put("entityMultiplierEntities", "Entities multiplied by entityMultiplierRate upon spawning");
            put("entityBlocklistEntities", "Entities which will be restricted from spawning");
            put("daylightResistantEntities", "These entities won't burn in the day (from daylight)");
            put("additionalZombieDrops", "List of additional drops and drop chances for zombies");
            put("heavilyArmoredZombiesEnabled", "Enable heavily armored zombies");
            put("dayCounterEnabled", "Enable day counter");
        }
    };

    public static void init(String configPath) {
        ConfigActions.init(configPath, defaults, comments);
        entityMultiplierRate = ConfigActions.readInt("entityMultiplierRate");
        entityMultiplierEntities = ConfigActions.readList("entityMultiplierEntities");
        entityBlocklistEntities = ConfigActions.readList("entityBlocklistEntities");
        daylightResistantEntities = ConfigActions.readList("daylightResistantEntities");
        additionalZombieDrops = ConfigActions.readList("additionalZombieDrops");
        heavilyArmoredZombiesEnabled = ConfigActions.readBool("heavilyArmoredZombiesEnabled");
        dayCounterEnabled = ConfigActions.readBool("dayCounterEnabled");
    }

    public static void onServerStopping(MinecraftServer server) {
        ConfigActions.close();
    }
}
