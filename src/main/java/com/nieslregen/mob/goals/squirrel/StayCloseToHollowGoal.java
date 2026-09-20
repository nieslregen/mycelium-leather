package com.nieslregen.mob.goals.squirrel;

import com.nieslregen.mob.myceliumsquirrel.MyceliumSquirrel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

public class StayCloseToHollowGoal extends Goal {

    private final MyceliumSquirrel squirrel;

    public StayCloseToHollowGoal(MyceliumSquirrel squirrel) {
        this.squirrel = squirrel;
    }

    @Override
    public boolean canUse() {
        return this.squirrel.getHomePos().isPresent()
                && !this.squirrel.getHomePos().get().closerToCenterThan(squirrel.position(), 25);
    }

    @Override
    public boolean canContinueToUse() {
        return this.squirrel.getHomePos().isPresent()
                && !this.squirrel.getHomePos().get().closerToCenterThan(squirrel.position(), 15);
    }

    @Override
    public void stop() {
        super.stop();
        squirrel.getNavigation().stop();
        squirrel.getMoveControl().setWantedPosition(
                squirrel.getX() + 0.5,
                squirrel.getY(),
                squirrel.getZ() + 0.5,
                1f);
    }

    @Override
    public void start() {
        BlockPos pos = this.squirrel.getHomePos().get();
        squirrel.getNavigation().moveTo(
                pos.getX() + 0.5,
                pos.getY(),
                pos.getZ() + 0.5,
                1.25
        );
    }

}
