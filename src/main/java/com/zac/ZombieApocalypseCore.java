package com.zac;

import net.fabricmc.api.ModInitializer;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zac.features.CustomZombieDrops;
import com.zac.features.DayCounter;
import com.zac.features.EntityHandler;
import com.zac.features.HeavilyArmorZombies;
import com.zac.features.NoDaylightBurning;

import net.minecraft.server.MinecraftServer;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class ZombieApocalypseCore implements ModInitializer {
	public static final String MOD_ID = "zac";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ServerLifecycleEvents.SERVER_STOPPING.register(Config::onServerStopping);
		Config.init(FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + "-common" + ".toml").toString());

		EntityHandler.register();
		DayCounter.register();
		HeavilyArmorZombies.register();
		NoDaylightBurning.register();
		CustomZombieDrops.register();

		// LOGGER.info("Hello Fabric world!");
		ServerTickEvents.END_SERVER_TICK.register(ZombieApocalypseCore::tick);
	}

	private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

	public static void queueServerWork(int tick, Runnable action) {
		workQueue.add(new AbstractMap.SimpleEntry<>(action, tick));
	}

	public static void tick(MinecraftServer server) {
		List<AbstractMap.SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
		workQueue.forEach(work -> {
			work.setValue(work.getValue() - 1);
			if (work.getValue() == 0)
				actions.add(work);
		});
		actions.forEach(e -> e.getKey().run());
		workQueue.removeAll(actions);
	}
}
