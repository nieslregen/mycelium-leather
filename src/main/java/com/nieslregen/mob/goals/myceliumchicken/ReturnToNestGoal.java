package com.nieslregen.mob.goals.myceliumchicken;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

public class ReturnToNestGoal extends Goal {
    private final MyceliumChicken chicken;

    public ReturnToNestGoal(MyceliumChicken chicken) {
        this.chicken = chicken;
    }

    @Override
    public boolean canUse() {
        return chicken.nestPos.isPresent()
                && !chicken.isAngry()
                && !chicken.nestPos.get()
                .closerToCenterThan(chicken.position(), 1.75);
    }

    @Override
    public void start() {
        MyceliumLeatherMod.LOGGER.info("Start ReturnToNestGoal");

        chicken.nestPos.ifPresent(pos -> chicken
                .getNavigation()
                .moveTo(
                        pos.getX() + 0.5,
                        pos.getY(),
                        pos.getZ() + 0.5,
                        1.25
                ));
    }

    @Override
    public boolean canContinueToUse() {
        return chicken.nestPos.isPresent()
                && !chicken.nestPos.get()
                .closerToCenterThan(chicken.position(), 0.75);
    }

    @Override
    public void stop() {
        MyceliumLeatherMod.LOGGER.info("End ReturnToNestGoal");
        chicken.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (chicken.nestPos.isPresent()) {
            BlockPos pos = chicken.nestPos.get();

            if (chicken.getNavigation().isDone()) {
                chicken.getNavigation().moveTo(
                        pos.getX() + 0.5,
                        pos.getY(),
                        pos.getZ() + 0.5,
                        1.25
                );
            }
        }
    }
}
