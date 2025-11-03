package com.zac.features;

import com.zac.Config;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class NoDaylightBurning {

	public static void register() {
		ServerTickEvents.START_WORLD_TICK.register(NoDaylightBurning::entityTickEvent);
	}

	public static void entityTickEvent(ServerLevel world) {
		for (Entity entity : world.getAllEntities()) {
			if (Config.daylightResistantEntities
					.contains(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString())) {
				if (entity.level().isDay()) {
					if (entity.level().canSeeSky(entity.blockPosition())) {
						if (entity.isOnFire() && !entity.isInLava()) {
							entity.clearFire();
						}
					}
				}
			}
		}
	}
}
