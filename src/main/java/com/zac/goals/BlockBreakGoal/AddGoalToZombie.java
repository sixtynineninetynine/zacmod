package com.zac.goals.BlockBreakGoal;

import com.zac.Config;

import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.fml.common.EventBusSubscriber;

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
