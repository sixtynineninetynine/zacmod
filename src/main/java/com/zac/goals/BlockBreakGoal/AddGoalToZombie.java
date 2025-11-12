package com.zac.goals.BlockBreakGoal;

import com.zac.Config;

import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class AddGoalToZombie {

    @SubscribeEvent
    public static void onZombieSpawn(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Zombie zombie) {
            if (Config.zombiesBreakBlocks) {
                zombie.goalSelector.addGoal(0, new BlockBreakGoal(zombie));
            }
        }
    }
}
