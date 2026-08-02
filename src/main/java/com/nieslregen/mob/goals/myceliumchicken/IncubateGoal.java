package com.nieslregen.mob.goals.myceliumchicken;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import com.nieslregen.mob.myceliumchicken.MyceliumChickenNestBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class IncubateGoal extends Goal {
    private final MyceliumChicken chicken;

    public IncubateGoal(MyceliumChicken chicken) {
        this.chicken = chicken;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public void start() {
        super.start();
        MyceliumLeatherMod.LOGGER.info("Start Incubating");
        if (chicken.nestPos.isPresent()){
            BlockState newState = chicken.level().getBlockState(chicken.nestPos.get()).setValue(MyceliumChickenNestBlock.IS_INCUBATING, true);
            chicken.level().setBlockAndUpdate(chicken.nestPos.get(), newState);

            chicken.level().setBlockAndUpdate(chicken.nestPos.get(), newState);

            if (chicken.carriesStolenEgg) {
                chicken.level().setBlockAndUpdate(
                        chicken.nestPos.get(),
                        chicken.level().getBlockState(chicken.nestPos.get()).setValue(MyceliumChickenNestBlock.HAS_EGG, true)
                );
                chicken.carriesStolenEgg = false;
            }

            chicken.getNavigation().stop();
        }
    }


    @Override
    public void stop() {
        MyceliumLeatherMod.LOGGER.info("End Incubating");
        super.stop();
        if (chicken.nestPos.isPresent()){
            BlockState newState = chicken.level().getBlockState(chicken.nestPos.get()).setValue(MyceliumChickenNestBlock.IS_INCUBATING, false);
            chicken.level().setBlockAndUpdate(chicken.nestPos.get(), newState);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (chicken.nestPos.isEmpty()) {
            return;
        }

        BlockPos pos = chicken.nestPos.get();

        if (chicken.getNavigation().isDone()) {
            double x = pos.getX() + 0.5;
            double z = pos.getZ() + 0.5;

            chicken.getMoveControl().setWantedPosition(
                    x,
                    pos.getY(),
                    z,
                    0.15
            );
        }
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && chicken.nestPos.isPresent() && chicken.nestPos.get().closerToCenterThan(chicken.position(), 1.5f);
    }

    @Override
    public boolean canUse() {
        return chicken.nestPos.isPresent() && chicken.nestPos.get().closerToCenterThan(chicken.position(), 1.5f);
    }
}
