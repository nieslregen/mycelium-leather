package com.nieslregen.mob.goals.myceliumchicken;

import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class DefendNestGoal extends MeleeAttackGoal {
    private final MyceliumChicken chicken;

    public DefendNestGoal(MyceliumChicken chicken) {
        super(chicken, 1.25f, true);
        this.chicken = chicken;
    }

    @Override
    public boolean canUse() {
        return chicken.isNestThreatened(5);
    }

    @Override
    public void start() {
        super.start();
        chicken.setTarget(chicken.nearbyMobs.getFirst());
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse()
                && chicken.isNestThreatened(5)
                && chicken.getTarget() != null && chicken.getTarget().isAlive();
    }




    @Override
    public void stop() {
        super.stop();
        chicken.setTarget(null);
        chicken.stopBeingAngry();
    }
}
