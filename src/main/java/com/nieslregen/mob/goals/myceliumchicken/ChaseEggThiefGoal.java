package com.nieslregen.mob.goals.myceliumchicken;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import com.nieslregen.mob.myceliumchicken.MyceliumChickenNestEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.block.state.BlockState;

public class ChaseEggThiefGoal extends MeleeAttackGoal {

    MyceliumChicken chicken;

    public ChaseEggThiefGoal(MyceliumChicken chicken) {
        super(chicken, 2.0f, true);
        this.chicken = chicken;
    }

    private boolean isAngryAtPlayer() {
        if (chicken.nestPos.isPresent()) {
            BlockPos p = this.chicken.nestPos.get();
            BlockState state = this.chicken.level().getBlockState(p);

            return chicken.level().getBlockEntity(p) instanceof MyceliumChickenNestEntity nestEntity
                    && nestEntity.getThief().isPresent()
                    && !nestEntity.hasEgg(state)
                    && !chicken.carriesStolenEgg;
        }
        return false;
    }

    @Override
    public boolean canUse() {
        return isAngryAtPlayer() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return isAngryAtPlayer() && super.canContinueToUse();
    }

    @Override
    public void start() {
        super.start();
        if  (chicken.nestPos.isPresent()) {
            if (chicken.level().getBlockEntity(chicken.nestPos.get()) instanceof MyceliumChickenNestEntity nestEntity && nestEntity.getThief().isPresent()) {
                chicken.setTarget(nestEntity.getThief().get());
            }
        }
    }

    @Override
    public void stop() {
        super.stop();
        chicken.setTarget(null);
        chicken.stopBeingAngry();
    }
}
