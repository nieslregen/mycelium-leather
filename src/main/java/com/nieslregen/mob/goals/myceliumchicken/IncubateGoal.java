package com.nieslregen.mob.goals.myceliumchicken;

import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import com.nieslregen.mob.myceliumchicken.MyceliumChickenNestBlock;
import com.nieslregen.mob.myceliumchicken.MyceliumChickenNestEntity;
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

        if (chicken.getNestPos().isPresent()){
            BlockPos pos = chicken.nestPos.get();
            BlockState newState = chicken.level().getBlockState(pos).setValue(MyceliumChickenNestBlock.IS_INCUBATING, true);
            MyceliumChickenNestEntity nestEntity = (MyceliumChickenNestEntity) chicken.level().getBlockEntity(pos);
            chicken.level().setBlockAndUpdate(pos, newState);

            if (chicken.carriesStolenEgg) {
                chicken.level().setBlockAndUpdate(
                        pos,
                        chicken.level().getBlockState(pos).setValue(MyceliumChickenNestBlock.HAS_EGG, true)
                );
                chicken.carriesStolenEgg = false;
                if (nestEntity != null) {
                    nestEntity.resetThief();
                }
            }

            chicken.setJumping(false);
            chicken.getNavigation().stop();
            chicken.setPos(
                    pos.getX() + 0.5,
                    pos.getY(),
                    pos.getZ() + 0.5);
            chicken.setSitting(true);
        }
    }


    @Override
    public void stop() {
        super.stop();
        if (chicken.getNestPos().isPresent()){
            BlockState newState = chicken.level().getBlockState(chicken.nestPos.get()).setValue(MyceliumChickenNestBlock.IS_INCUBATING, false);
            chicken.level().setBlockAndUpdate(chicken.nestPos.get(), newState);
        }
        chicken.setSitting(false);
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse()
                && chicken.getNestPos().isPresent()
                && chicken.nestPos.get().closerToCenterThan(chicken.position(), 1.5f)
                && ((!chicken.carriesStolenEgg && nestHasEgg()) || (chicken.carriesStolenEgg && !nestHasEgg()))
                && !chicken.isNestThreatened(5);
    }

    @Override
    public boolean canUse() {
        return chicken.getNestPos().isPresent()
                && chicken.nestPos.get().closerToCenterThan(chicken.position(), 1.5f)
                && ((!chicken.carriesStolenEgg && nestHasEgg()) || (chicken.carriesStolenEgg && !nestHasEgg()))
                && !chicken.isNestThreatened(5);
    }



    private boolean nestHasEgg() {
        if (this.chicken.getNestPos().isPresent()) {
            if (this.chicken.level().getBlockEntity(this.chicken.nestPos.get()) instanceof MyceliumChickenNestEntity nest) {
                return nest.hasEgg(this.chicken.level().getBlockState(this.chicken.nestPos.get()));
            }
        }
        return false;
    }
}
