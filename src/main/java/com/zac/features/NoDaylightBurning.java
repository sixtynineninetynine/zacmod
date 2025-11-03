package com.zac.features;

import com.zac.Config;

import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;

@EventBusSubscriber
public class NoDaylightBurning {

	@SubscribeEvent
	@SuppressWarnings("deprecation")
	public static void entityTickEvent(LivingEvent.LivingTickEvent event) {
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
