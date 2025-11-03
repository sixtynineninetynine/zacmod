package com.zac.features;

import com.zac.Config;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.event.entity.living.MobSpawnEvent.FinalizeSpawn;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.Difficulty;

@EventBusSubscriber
public class HeavilyArmorZombies {

    public static void armorEntity(Entity entity, EquipmentSlot slot, ItemStack item) {
        if (entity instanceof LivingEntity living) {
            living.setItemSlot(slot, item);
        }
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

    @SubscribeEvent
    public static void onEntitySpawn(FinalizeSpawn event) {
        if (Config.heavilyArmoredZombiesEnabled) {
            LevelAccessor world = event.getLevel();
            Entity entity = event.getEntity();
            if (entity != null && entity instanceof Zombie) {
                if (world.getDifficulty() == Difficulty.HARD || world.getDifficulty() == Difficulty.NORMAL) {
                    whatAreTheChances(entity);
                }
            }
        }
    }
}
