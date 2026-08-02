package com.nieslregen.mob.goals.squirrel;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.block.ModBlocks;
import com.nieslregen.mob.ModPoiTypes;
import com.nieslregen.mob.myceliumsquirrel.MyceliumSquirrel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;

import java.util.Comparator;
import java.util.Optional;

public class LocateHollowGoal extends Goal {

    private final MyceliumSquirrel squirrel;

    public LocateHollowGoal(MyceliumSquirrel squirrel) {
        this.squirrel = squirrel;
    }

    @Override
    public boolean canUse() {
        return this.squirrel.getHomePos().isEmpty()
                && this.squirrel.needsToRest;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        MyceliumLeatherMod.LOGGER.info("Search for hollow");
        squirrel.resetTimeUntilResting();
        Optional<BlockPos> p = findNest((ServerLevel) squirrel.level(), squirrel.blockPosition(), 15);
        MyceliumLeatherMod.LOGGER.error("Found: {}", p);
        squirrel.homePos = p;
    }

    public static Optional<BlockPos> findNest(
            ServerLevel level,
            BlockPos center,
            int radius
    ) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    mutable.set(
                            center.getX() + x,
                            center.getY() + y,
                            center.getZ() + z
                    );

                    if (level.getBlockState(mutable)
                            .is(ModBlocks.MUSHROOM_STEM_HOLLOW)) {

                        return Optional.of(mutable.immutable());
                    }
                }
            }
        }

        return Optional.empty();
    }

    private Optional<BlockPos> xfindHollowWithSpace() {
        BlockPos squirrelPos = squirrel.blockPosition();
        PoiManager poiManager = ((ServerLevel) squirrel.level()).getPoiManager();

        return poiManager.getInRange((p) ->
                                p.is(ModPoiTypes.SQUIRREL_HOME),
                        squirrelPos,
                        20,
                        PoiManager.Occupancy.ANY)
                .map(PoiRecord::getPos)
                .peek(x -> MyceliumLeatherMod.LOGGER.info("Hollow found at [{}]", x))
                .filter(this::doesHollowHaveSpace)
                .sorted(Comparator.comparingDouble((pos) -> pos.distSqr(squirrelPos)))
                .findFirst();
    }

    private boolean doesHollowHaveSpace(final BlockPos pos) {
        // ToDo: check if occupant limit is reached
        return squirrel.level().getBlockState(pos).is(ModBlocks.MUSHROOM_STEM_HOLLOW);
    }

}
