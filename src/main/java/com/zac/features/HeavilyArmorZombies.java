package com.zac.features;

import com.zac.Config;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;

public class HeavilyArmorZombies {

    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register(HeavilyArmorZombies::onEntitySpawn);
    }

    public static void armorEntity(Entity entity, EquipmentSlot slot, ItemStack item) {
        if (entity instanceof LivingEntity living) {
            living.setItemSlot(slot, item);
        }
        /*
         * forge's and neoforge's finalize spawn event didn't get triggered each world
         * load but fabric doesn't have a finalize spawn event so i had to use
         * ENTITY_LOAD which gets triggered each time the player joins a world all
         * entities load once again, every zombie gets new armor for absolutely no
         * reason, which makes this necessary to save performance
         * 
         * i suppose there's a better way to do this that i do not know of
         */
        entity.addTag("alreadyArmored");
    }

    public static void whatAreTheChances(Entity entity) {
        // yes, i did not have a better name for this
        if (Math.random() < 0.15) {
            if (Math.random() < 0.6) {
                if (Math.random() < 0.4) {
                    armorEntity(entity, EquipmentSlot.HEAD,
                            new ItemStack(Items.IRON_HELMET));
                    armorEntity(entity, EquipmentSlot.LEGS,
                            new ItemStack(Items.IRON_LEGGINGS));
                    armorEntity(entity, EquipmentSlot.FEET,
                            new ItemStack(Items.LEATHER_BOOTS));
                } else {
                    armorEntity(entity, EquipmentSlot.CHEST,
                            new ItemStack(Items.LEATHER_CHESTPLATE));
                    armorEntity(entity, EquipmentSlot.FEET,
                            new ItemStack(Items.CHAINMAIL_BOOTS));
                }
            }
        } else if (Math.random() < 0.5) {
            if (Math.random() < 0.4) {
                armorEntity(entity, EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
                armorEntity(entity, EquipmentSlot.LEGS,
                        new ItemStack(Items.GOLDEN_LEGGINGS));
            } else {
                armorEntity(entity, EquipmentSlot.CHEST,
                        new ItemStack(Items.IRON_CHESTPLATE));
                armorEntity(entity, EquipmentSlot.LEGS,
                        new ItemStack(Items.CHAINMAIL_LEGGINGS));
            }
        } else if (Math.random() < 0.7) {
            if (Math.random() < 0.5) {
                armorEntity(entity, EquipmentSlot.HEAD,
                        new ItemStack(Items.DIAMOND_HELMET));
                armorEntity(entity, EquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS));
            } else {
                armorEntity(entity, EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
                armorEntity(entity, EquipmentSlot.CHEST,
                        new ItemStack(Items.CHAINMAIL_CHESTPLATE));
                armorEntity(entity, EquipmentSlot.LEGS,
                        new ItemStack(Items.GOLDEN_LEGGINGS));
            }
        } else if (Math.random() < 0.9) {
            armorEntity(entity, EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
            armorEntity(entity, EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
        } else if (Math.random() < 0.85) {
            armorEntity(entity, EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
            armorEntity(entity, EquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
        }
    }

    public static void onEntitySpawn(Entity entity, ServerLevel level) {
        if (Config.heavilyArmoredZombiesEnabled) {
            LevelAccessor world = level.getLevel();
            if (entity != null && entity instanceof Zombie) {
                if (!entity.getTags().contains("alreadyArmored")) {
                    if (world.getDifficulty() == Difficulty.HARD || world.getDifficulty() == Difficulty.NORMAL) {
                        whatAreTheChances(entity);
                    }
                }
            }
        }
    }
}
