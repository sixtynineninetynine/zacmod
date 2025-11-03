package com.zac;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

        private static final Predicate<Object> elementValidator = o -> (o instanceof String s) && !s.trim().isEmpty();

        private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

        private static final ForgeConfigSpec.IntValue ENTITY_MULTIPLIER_RATE = BUILDER.comment(
                        "PLEASE READ THE INSTRUCTIONS ON THE MOD PAGE TO PREVENT MISTAKES!\n---")
                        .defineInRange("entityMultiplierRate", 1, 1, 99);

        private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ENTITY_MULTIPLIER_ENTITIES = BUILDER
                        .comment("Entities multiplied by entityMultiplierRate upon spawning")
                        .defineListAllowEmpty("entityMultiplierEntities", List.of(), elementValidator);

        private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ENTITY_BLOCKLIST_ENTITIES = BUILDER
                        .comment("Entities which will be restricted from spawning")
                        .defineListAllowEmpty("entityBlocklistEntities", List.of(), elementValidator);

        private static final ForgeConfigSpec.ConfigValue<List<? extends String>> DAYLIGHT_RESISTANT_ENTITIES = BUILDER
                        .comment("These entities won't burn in the day (from daylight)")
                        .defineListAllowEmpty("daylightResistantEntities", List.of("minecraft:zombie"),
                                        elementValidator);

        private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ADDITIONAL_ZOMBIE_DROPS = BUILDER
                        .comment("List of additional drops and drop chances for zombies")
                        .defineListAllowEmpty("additionalZombieDrops",
                                        List.of("minecraft:rotten_flesh=0.9", "minecraft:bone=0.1",
                                                        "minecraft:carrot=0.009", "minecraft:potato=0.0009",
                                                        "minecraft:iron_nugget=0.0005", "minecraft:gold_nugget=0.0001"),
                                        elementValidator);

        private static final ForgeConfigSpec.BooleanValue HEAVILY_ARMORED_ZOMBIES_ENABLED = BUILDER
                        .comment("Enable heavily armored zombies")
                        .define("heavilyArmoredZombiesEnabled", false);

        private static final ForgeConfigSpec.BooleanValue DAY_COUNTER_ENABLED = BUILDER
                        .comment("Enable day counter")
                        .define("dayCounterEnabled", true);

        public static final ForgeConfigSpec SPEC = BUILDER.build();

        public static int entityMultiplierRate;
        public static List<String> entityMultiplierEntities;
        public static List<String> entityBlocklistEntities;
        public static List<String> daylightResistantEntities;
        public static List<String> additionalZombieDrops;
        public static boolean heavilyArmoredZombiesEnabled;
        public static boolean dayCounterEnabled;

        @SubscribeEvent
        static void onLoad(final ModConfigEvent event) {
                entityMultiplierRate = ENTITY_MULTIPLIER_RATE.get();
                entityMultiplierEntities = new ArrayList<>(ENTITY_MULTIPLIER_ENTITIES.get());
                entityBlocklistEntities = new ArrayList<>(ENTITY_BLOCKLIST_ENTITIES.get());
                daylightResistantEntities = new ArrayList<>(DAYLIGHT_RESISTANT_ENTITIES.get());
                additionalZombieDrops = new ArrayList<>(ADDITIONAL_ZOMBIE_DROPS.get());
                heavilyArmoredZombiesEnabled = HEAVILY_ARMORED_ZOMBIES_ENABLED.get();
                dayCounterEnabled = DAY_COUNTER_ENABLED.get();
        }
}
