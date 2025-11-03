package com.zac;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.level.Level;

public class WorldData extends SavedData {

    private int dayCount;

    public WorldData() {
        this.dayCount = 0;
    }

    public static WorldData load(CompoundTag tag, Provider lookupProvider) {
        WorldData data = WorldData.create();
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, Provider registries) {
        return tag;
    }

    public void setDay(int day) {
        this.dayCount = day;
        this.setDirty();
    }

    public int getDay() {
        return this.dayCount;
    }

    public static WorldData create() {
        return new WorldData();
    }

    public static WorldData get(ServerLevel level) {
        ServerLevel world = level.getServer().getLevel(Level.OVERWORLD);
        if (world != null) {
            return world.getDataStorage().computeIfAbsent(new Factory<>(WorldData::create, WorldData::load, null),
                    "worlddata");
        } else {
            return null;
        }
    }
}
