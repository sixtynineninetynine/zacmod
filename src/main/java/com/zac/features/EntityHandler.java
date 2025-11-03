package com.zac.features;

import com.zac.Config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;

@EventBusSubscriber
public class EntityHandler {

    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        String entityRegistryName = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
        // entity blocklist
        if (Config.entityBlocklistEntities.contains(entityRegistryName)) {
            if (entity instanceof Mob && !entity.level().isClientSide) {
                event.setCanceled(true);
            }
        } else {
            // entity multiplier
            if (Config.entityMultiplierEntities.contains(entityRegistryName)) {
                if (entity instanceof Mob && !entity.getTags().contains("multiplied")) {
                    entity.addTag("multiplied");
                    int multiplier = (int) Config.entityMultiplierRate;
                    if (multiplier > 1 && entity.level() instanceof ServerLevel level) {
                        for (int i = 0; i < multiplier - 1; i++) {
                            EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE
                                    .get(ResourceLocation.parse(entityRegistryName));
                            if (entityType != null) {
                                Entity newEntity = entityType.create(level);
                                if (newEntity != null) {
                                    double offsetX = entity.getX() + (Math.random() - 0.5);
                                    double offsetY = entity.getY();
                                    double offsetZ = entity.getZ() + (Math.random() - 0.5);
                                    newEntity.moveTo(offsetX, offsetY, offsetZ, entity.getYRot(),
                                            entity.getXRot());
                                    newEntity.addTag("multiplied");
                                    level.addFreshEntity(newEntity);
                                    HeavilyArmorZombies.whatAreTheChances(newEntity);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
