package com.nieslregen.mob.goals.squirrel;

import com.nieslregen.block.ModBlocks;
import com.nieslregen.block.custom.mushroomstem.MushroomStemHollowEntity;
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
        return squirrel.bufferFindHollow <= 0
                && (squirrel.getHomePos().isEmpty() || squirrel.getHomePos().get().closerThan(squirrel.blockPosition(), 128));
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        squirrel.resetTimeUntilResting();
        Optional<BlockPos> p = findNest();
        squirrel.homePos = p;
    }

    @Override
    public void stop() {
        super.stop();
        squirrel.resetBufferFindHollow();
    }

    private Optional<BlockPos> findNest() {
        BlockPos squirrelPos = squirrel.blockPosition();
        PoiManager poiManager = ((ServerLevel) squirrel.level()).getPoiManager();

        return poiManager.getInRange((p) ->
                        p.is(ModPoiTypes.SQUIRREL_HOME_POI_KEY),
                        squirrelPos,
                        30,
                        PoiManager.Occupancy.ANY)
                .map(PoiRecord::getPos)
//                .filter(this::doesHollowHaveSpace)
                .min(Comparator.comparingDouble((pos) -> pos.distSqr(squirrelPos)));
    }

    private boolean doesHollowHaveSpace(final BlockPos pos) {
        if (squirrel.level().getBlockEntity(pos) instanceof MushroomStemHollowEntity hollow) {
            return squirrel.level().getBlockState(pos).is(ModBlocks.MUSHROOM_STEM_HOLLOW)
                    && !hollow.isFull();
        }
        return false;
    }
}
