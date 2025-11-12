package com.zac.goals.BlockBreakGoal;

import com.zac.Config;

import java.util.EnumSet;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class BlockBreakGoal extends Goal {

    private final Zombie zombie;

    public BlockBreakGoal(Zombie zombie) {
        this.zombie = zombie;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = zombie.getTarget();
        Level level = zombie.level();
        if (target instanceof Player player) {
            BlockPos blockPos = getClosestBlock(zombie, player);
            if (blockPos != null) {
                BlockState blockState = level.getBlockState(blockPos);
                if (blockState.getDestroySpeed(level, blockPos) > Config.blockMinimumHardness
                        && blockState.getDestroySpeed(level, blockPos) <= Config.blockMaximumHardness) {
                    Vec3 entityPos = zombie.position();
                    Vec3 blockCenter = Vec3.atCenterOf(blockPos);
                    double dist = entityPos.distanceToSqr(blockCenter);
                    if (!level.isClientSide) {
                        if (dist <= Config.zombieBlockBreakReach * Config.zombieBlockBreakReach) {
                            level.destroyBlock(blockPos, true);
                        }
                    }
                }
            }
        }
    }

    public static BlockPos getClosestBlock(LivingEntity entityOne, LivingEntity entityTwo) {
        Vec3 from = entityOne.getEyePosition(1.0F);
        // Vec3 to = entityTwo.getEyePosition(1.0F);
        Vec3 to = entityTwo.position();
        to = to.add(0, 0.5, 0); // make sure that entityOne can break blocks that are just above the ground

        BlockPos blockpos = null;

        ClipContext clipcontext = new ClipContext(
                from,
                to,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                entityOne);

        BlockHitResult blockhits = entityOne.level().clip(clipcontext);

        if (blockhits.getType() == HitResult.Type.BLOCK) {
            blockpos = blockhits.getBlockPos();
        }

        return blockpos;
    }
}
