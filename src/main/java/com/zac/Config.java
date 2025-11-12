package com.zac;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = ZombieApocalypseCore.MODID)
public class Config {

        private static final Predicate<Object> elementValidator = o -> (o instanceof String s) && !s.trim().isEmpty();

        private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

        private static final ModConfigSpec.IntValue ENTITY_MULTIPLIER_RATE = BUILDER.comment(
                        "PLEASE READ THE INSTRUCTIONS ON THE MOD PAGE TO PREVENT MISTAKES!\n---")
                        .defineInRange("entityMultiplierRate", 1, 1, 99);

        private static final ModConfigSpec.ConfigValue<List<? extends String>> ENTITY_MULTIPLIER_ENTITIES = BUILDER
                        .comment("Entities multiplied by entityMultiplierRate upon spawning")
                        .defineListAllowEmpty("entityMultiplierEntities", List.of(), () -> "", elementValidator);

        private static final ModConfigSpec.ConfigValue<List<? extends String>> ENTITY_BLOCKLIST_ENTITIES = BUILDER
                        .comment("Entities which will be restricted from spawning")
                        .defineListAllowEmpty("entityBlocklistEntities", List.of(), () -> "", elementValidator);

        private static final ModConfigSpec.ConfigValue<List<? extends String>> DAYLIGHT_RESISTANT_ENTITIES = BUILDER
                        .comment("These entities won't burn in the day (from daylight)")
                        .defineListAllowEmpty("daylightResistantEntities", List.of("minecraft:zombie"), () -> "",
                                        elementValidator);

        private static final ModConfigSpec.ConfigValue<List<? extends String>> ADDITIONAL_ZOMBIE_DROPS = BUILDER
                        .comment("List of additional drops and drop chances for zombies")
                        .defineListAllowEmpty("additionalZombieDrops",
                                        List.of("minecraft:rotten_flesh=0.9", "minecraft:bone=0.1",
                                                        "minecraft:carrot=0.009", "minecraft:potato=0.0009",
                                                        "minecraft:iron_nugget=0.0005", "minecraft:gold_nugget=0.0001"),
                                        () -> "", elementValidator);

        private static final ModConfigSpec.BooleanValue HEAVILY_ARMORED_ZOMBIES_ENABLED = BUILDER
                        .comment("Enable heavily armored zombies")
                        .define("heavilyArmoredZombiesEnabled", false);

        private static final ModConfigSpec.BooleanValue DAY_COUNTER_ENABLED = BUILDER
                        .comment("Enable day counter")
                        .define("dayCounterEnabled", true);

        private static final ModConfigSpec.BooleanValue ZOMBIES_BREAK_BLOCKS = BUILDER
                        .comment("Whether zombies can break blocks or not")
                        .define("zombiesBreakBlocks", true);

        private static final ModConfigSpec.IntValue ZOMBIE_BLOCK_BREAK_REACH = BUILDER
                        .comment("Block breaking reach of zombies")
                        .defineInRange("zombieBlockBreakReach", 3, 1, 25);

        private static final ModConfigSpec.IntValue BLOCK_MINIMUM_HARDNESS = BUILDER
                        .comment("Minimum hardness of blocks that should be broken")
                        .defineInRange("blockMinimumHardness", 0, -1, 50);

        private static final ModConfigSpec.IntValue BLOCK_MAXIMUM_HARDNESS = BUILDER
                        .comment("Maximum hardness of blocks that should be broken")
                        .defineInRange("blockMaximumHardness", 3, 0, 51);

        public static final ModConfigSpec SPEC = BUILDER.build();

        public static int entityMultiplierRate;
        public static List<String> entityMultiplierEntities;
        public static List<String> entityBlocklistEntities;
        public static List<String> daylightResistantEntities;
        public static List<String> additionalZombieDrops;
        public static boolean heavilyArmoredZombiesEnabled;
        public static boolean dayCounterEnabled;
        public static boolean zombiesBreakBlocks;
        public static int zombieBlockBreakReach;
        public static int blockMinimumHardness;
        public static int blockMaximumHardness;

        @SubscribeEvent
        static void onLoad(final ModConfigEvent event) {
                entityMultiplierRate = ENTITY_MULTIPLIER_RATE.get();
                entityMultiplierEntities = new ArrayList<>(ENTITY_MULTIPLIER_ENTITIES.get());
                entityBlocklistEntities = new ArrayList<>(ENTITY_BLOCKLIST_ENTITIES.get());
                daylightResistantEntities = new ArrayList<>(DAYLIGHT_RESISTANT_ENTITIES.get());
                additionalZombieDrops = new ArrayList<>(ADDITIONAL_ZOMBIE_DROPS.get());
                heavilyArmoredZombiesEnabled = HEAVILY_ARMORED_ZOMBIES_ENABLED.get();
                dayCounterEnabled = DAY_COUNTER_ENABLED.get();
                zombiesBreakBlocks = ZOMBIES_BREAK_BLOCKS.get();
                zombieBlockBreakReach = ZOMBIE_BLOCK_BREAK_REACH.get();
                blockMinimumHardness = BLOCK_MINIMUM_HARDNESS.get();
                blockMaximumHardness = BLOCK_MAXIMUM_HARDNESS.get();
        }
}
