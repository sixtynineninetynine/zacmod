package com.zac.features;

import com.zac.Config;

import java.util.ArrayList;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;

@EventBusSubscriber
public class CustomZombieDrops {

    private static void spawnDrops(ServerLevel world, double x, double y, double z,
            ItemStack itemStack) {
        ItemEntity entityToSpawn = new ItemEntity(world, x, y, z, itemStack);
        entityToSpawn.setPickUpDelay(10);
        world.addFreshEntity(entityToSpawn);
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDropsEvent event) {
        if (!Config.additionalZombieDrops.isEmpty()) {
            Entity entity = event.getEntity();
            if (entity != null && entity.level() instanceof ServerLevel world) {
                double x = entity.getX();
                double y = entity.getY();
                double z = entity.getZ();
                if (entity instanceof Zombie) {
                    ArrayList<String> drops = new ArrayList<>(Config.additionalZombieDrops);
                    for (String drop : drops) {
                        String[] splitDrop = drop.trim().split("=");
                        Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(splitDrop[0].toLowerCase()));
                        double chance = Double.parseDouble(splitDrop[1]);
                        if (Math.random() < chance) {
                            spawnDrops(world, x, y, z, new ItemStack(item));
                        }
                    }
                }
            }
        }
    }
}