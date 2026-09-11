package com.nieslregen.mob.goals.myceliumchicken;

import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import com.nieslregen.mob.myceliumchicken.MyceliumChickenNestEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.block.state.BlockState;

public class ChaseEggThiefGoal extends MeleeAttackGoal {

    MyceliumChicken chicken;

    public ChaseEggThiefGoal(MyceliumChicken chicken) {
        super(chicken, 1.5f, true);
        this.chicken = chicken;
    }

    private boolean isAngryAtPlayer() {
        if (chicken.getNestPos().isPresent()) {
            BlockPos p = this.chicken.getNestPos().get();
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
        // is not allowed to call super.canUse because the target is not set in this case hence the goal will never start.
        return isAngryAtPlayer(); //&& super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return isAngryAtPlayer() && super.canContinueToUse();
    }

    @Override
    public void start() {
        super.start();
        if  (chicken.getNestPos().isPresent()) {
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
