package com.zac;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.server.level.ServerLevel;

public class WorldData extends SavedData {

    private int dayCount;

    public WorldData() {
        this.dayCount = 0;
    }

    public static WorldData load(CompoundTag tag) {
        WorldData data = new WorldData();
        data.dayCount = tag.getInt("dayCount");
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putInt("dayCount", this.dayCount);
        return tag;
    }

    public void setDay(int day) {
        this.dayCount = day;
        this.setDirty();
    }

    public int getDay() {
        return this.dayCount;
    }

    public static WorldData get(ServerLevel level) {
        ServerLevel world = level.getServer().getLevel(Level.OVERWORLD);
        if (world != null) {
            return world.getDataStorage().computeIfAbsent(WorldData::load, WorldData::new, "worlddata");
        } else {
            return null;
        }
    }
}
