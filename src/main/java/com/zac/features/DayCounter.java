package com.zac.features;

import com.zac.WorldData;
import com.zac.ZombieApocalypseCore;
import com.zac.Config;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class DayCounter {

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(DayCounter::onPlayerTick);
    }

    public static void playSoundAndSendMsg(Player player, String msg) {
        SoundEvent sound = SoundEvents.STONE_BUTTON_CLICK_ON;
        BlockPos blockpos = BlockPos.containing(player.getX(), player.getY(), player.getZ());
        player.level().playSound(null,
                blockpos, sound, SoundSource.NEUTRAL, 3, 3);
        player.displayClientMessage(Component.literal(msg), true);
    }

    private static String[] messages = { "-", "--", "- -", "- D -", "- DA -", "- DAY -", "- DAY X -" };
    private static final HashMap<UUID, Integer> time = new HashMap<>();

    public static void onPlayerTick(MinecraftServer server) {
        PlayerList playerList = server.getPlayerList();
        for (ServerPlayer player : playerList.getPlayers()) {
            if (Config.dayCounterEnabled) {
                if (player != null) {
                    LevelAccessor world = player.level();
                    if (!world.isClientSide() && world instanceof ServerLevel level) {
                        if (world.dayTime() % 24000 == 1) {
                            WorldData data = WorldData.get(level);
                            int day = data.getDay() + 1;
                            data.setDay(day);
                            UUID playerUUID = player.getUUID();
                            time.put(playerUUID, 0);
                            int savedTime = time.get(playerUUID);
                            for (String message : messages) {
                                savedTime += 10;
                                time.put(playerUUID, savedTime);
                                ZombieApocalypseCore.queueServerWork(savedTime, () -> {
                                    playSoundAndSendMsg(player, message.replace("X", String.valueOf(day)));
                                });
                            }
                            time.put(player.getUUID(), 0);
                        }
                    }
                }
            }
        }
    }
}
