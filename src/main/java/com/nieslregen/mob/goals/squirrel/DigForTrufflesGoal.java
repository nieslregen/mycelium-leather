package com.nieslregen.mob.goals.squirrel;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.items.ModItems;
import com.nieslregen.mob.myceliumsquirrel.MyceliumSquirrel;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class DigForTrufflesGoal extends Goal {
    private final MyceliumSquirrel squirrel;
    private int diggingCounter;

    public DigForTrufflesGoal(MyceliumSquirrel squirrel) {
        this.squirrel = squirrel;
    }

    @Override
    public boolean canUse() {
        return squirrel.digTimer <= 0;
    }

    @Override
    public void start() {
        super.start();
        diggingCounter = 40;
        this.squirrel.setDigging(true);
        this.squirrel.setJumping(false);
        this.squirrel.getNavigation().stop();
        this.squirrel.getMoveControl().setWantedPosition(this.squirrel.getX(), this.squirrel.getY(), this.squirrel.getZ(), (double)0.0F);
    }

    @Override
    public void tick() {
        super.tick();
        diggingCounter--;
    }

    @Override
    public void stop() {
        super.stop();
        diggingCounter = 40;
        squirrel.setDigging(false);
        squirrel.resetDigTimer();
        if (squirrel.level().getBlockState(squirrel.blockPosition().below()).is(Blocks.MYCELIUM)) {
            int randomizedDrop = squirrel.getRandom().nextInt(1, 3);
            Block.popResourceFromFace(squirrel.level(), squirrel.blockPosition().below(), Direction.UP, new ItemStack(ModItems.TRUFFLE, randomizedDrop));
        }
    }

    @Override
    public boolean canContinueToUse() {
        return diggingCounter > 0;
    }
}
