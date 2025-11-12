package com.zac.mixin;

import com.zac.Config;
import com.zac.goals.BlockBreakGoal.BlockBreakGoal;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

@Mixin(Zombie.class)
public class ZombieMixin extends Mob {

	protected ZombieMixin(EntityType<? extends Mob> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(at = @At("TAIL"), method = "addBehaviourGoals")
	private void init(CallbackInfo info) {
		if (Config.zombiesBreakBlocks) {
			this.goalSelector.addGoal(0, new BlockBreakGoal((Zombie) (Object) this));
		}
	}
}
