package com.nieslregen.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.Path;
import org.jspecify.annotations.Nullable;

public class CustomClimbingNavigation extends GroundPathNavigation {

    // lookup WallClimbingNavigation for reference
    // these changes are temporary to reduce endless spinning of mobs

    private @Nullable BlockPos pathToPosition;

    public CustomClimbingNavigation(final Mob mob, final Level level) {
        super(mob, level);
    }

    public Path createPath(final BlockPos pos, final int reachRange) {
        this.pathToPosition = pos;
        return super.createPath(pos, reachRange);
    }

    public Path createPath(final Entity target, final int reachRange) {
        this.pathToPosition = target.blockPosition();
        return super.createPath(target, reachRange);
    }

    public boolean moveTo(final Entity target, final double speedModifier) {
        Path newPath = this.createPath(target, 0);
        if (newPath != null) {
            return this.moveTo(newPath, speedModifier);
        } else {
            this.pathToPosition = target.blockPosition();
            this.speedModifier = speedModifier;
            return true;
        }
    }


    public void tick() {
        if (!this.isDone()) {
            super.tick();
        } else {
            if (this.pathToPosition != null) {
                if (!this.pathToPosition.closerToCenterThan(this.mob.position(), this.mob.getBbWidth())
                        && (!(this.mob.getY() > this.pathToPosition.getY())
                                || !BlockPos.containing(
                                        this.pathToPosition.getX(),
                                        this.mob.getY(),
                                        this.pathToPosition.getZ())
                                    .closerToCenterThan(this.mob.position(), this.mob.getBbWidth()))
                        && isBlockNearby(mob)) {


                    this.mob.getMoveControl()
                            .setWantedPosition(
                                    this.pathToPosition.getX(),
                                    this.pathToPosition.getY(),
                                    this.pathToPosition.getZ(),
                                    this.speedModifier);
                } else {
                    this.pathToPosition = null;
                }
            }

        }
    }

    // ToDo: change this so it check if there is a climbable block like wood, because foliage triggers this check as well

    private boolean isBlockNearby(Mob mob) {
        return !mob.level().getBlockState(mob.blockPosition().north()).is(Blocks.AIR)
                || !mob.level().getBlockState(mob.blockPosition().south()).is(Blocks.AIR)
                || !mob.level().getBlockState(mob.blockPosition().west()).is(Blocks.AIR)
                || !mob.level().getBlockState(mob.blockPosition().east()).is(Blocks.AIR);
    }
}
