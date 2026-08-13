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

        if (chicken.nestPos.isPresent()){
            BlockPos pos = chicken.nestPos.get();
            BlockState newState = chicken.level().getBlockState(pos).setValue(MyceliumChickenNestBlock.IS_INCUBATING, true);
            chicken.level().setBlockAndUpdate(pos, newState);

            if (chicken.carriesStolenEgg) {
                chicken.level().setBlockAndUpdate(
                        pos,
                        chicken.level().getBlockState(pos).setValue(MyceliumChickenNestBlock.HAS_EGG, true)
                );
                chicken.carriesStolenEgg = false;
            }

            chicken.getNavigation().stop();
            chicken.setPos(pos.getX(), pos.getY(), pos.getZ());
            chicken.sitDown();
        }
    }


    @Override
    public void stop() {
        super.stop();
        if (chicken.nestPos.isPresent()){
            BlockState newState = chicken.level().getBlockState(chicken.nestPos.get()).setValue(MyceliumChickenNestBlock.IS_INCUBATING, false);
            chicken.level().setBlockAndUpdate(chicken.nestPos.get(), newState);
        }
        chicken.standUp();
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
