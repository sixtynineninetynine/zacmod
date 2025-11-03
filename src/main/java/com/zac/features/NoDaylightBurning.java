package com.zac.features;

import com.zac.Config;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class NoDaylightBurning {

	@SubscribeEvent
	public static void entityTickEvent(EntityTickEvent.Pre event) {
		Entity entity = event.getEntity();
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
