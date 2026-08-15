package com.nieslregen.mob.goals.squirrel;

import com.nieslregen.items.ModItems;
import com.nieslregen.mob.myceliumsquirrel.MyceliumSquirrel;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class DigForTrufflesGoal extends Goal {
    private final MyceliumSquirrel squirrel;

    public DigForTrufflesGoal(MyceliumSquirrel squirrel) {
        this.squirrel = squirrel;
    }

    @Override
    public boolean canUse() {
        return squirrel.digTimer <= 0
                && !squirrel.needsToRest;
    }

    @Override
    public void stop() {
        super.stop();
        squirrel.resetDigTimer();
        if (squirrel.level().getBlockState(squirrel.blockPosition().below()).is(Blocks.MYCELIUM)) {
            int randomizedDrop = squirrel.getRandom().nextInt(1, 3);
            Block.popResourceFromFace(squirrel.level(), squirrel.blockPosition().below(), Direction.UP, new ItemStack(ModItems.TRUFFLE, randomizedDrop));
        }
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }
}
