package com.nieslregen.mob.goals.squirrel;

import com.nieslregen.mob.myceliumsquirrel.MyceliumSquirrel;
import net.minecraft.world.entity.ai.goal.Goal;

public class GoHomeGoal extends Goal {
    private final MyceliumSquirrel squirrel;

    public GoHomeGoal(MyceliumSquirrel squirrel) {
        this.squirrel = squirrel;
    }

    @Override
    public void start() {
        squirrel.homePos.ifPresent(pos -> squirrel
                .getNavigation()
                .moveTo(
                        pos.getX() + 0.5,
                        pos.getY(),
                        pos.getZ() + 0.5,
                        1.25
                ));
    }

    @Override
    public void stop() {
        super.stop();
        squirrel.resetTimeUntilResting();
        squirrel.getNavigation().stop();
    }


    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse()
                && squirrel.wantsToGoHome
                && squirrel.homePos.isPresent()
                && !squirrel.homePos.get()
                .closerToCenterThan(squirrel.position(), 0.75);
    }

    @Override
    public boolean canUse() {
        return squirrel.homePos.isPresent()
                && squirrel.wantsToGoHome;
    }
}
