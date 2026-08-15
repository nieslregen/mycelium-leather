package com.nieslregen.mob.goals.squirrel;

import com.nieslregen.block.custom.mushroomstem.MushroomStemHollowEntity;
import com.nieslregen.mob.CustomOccupant;
import com.nieslregen.mob.ModEntityTypes;
import com.nieslregen.mob.myceliumsquirrel.MyceliumSquirrel;
import net.minecraft.world.entity.ai.goal.Goal;

public class EnterHollowGoal extends Goal {
    private final MyceliumSquirrel squirrel;

    public EnterHollowGoal(MyceliumSquirrel squirrel) {
        this.squirrel = squirrel;
    }

    @Override
    public void start() {
        super.start();
        squirrel.homePos.ifPresent(pos -> {
            MushroomStemHollowEntity hollow = (MushroomStemHollowEntity) squirrel.level().getBlockEntity(pos);
            if (hollow != null) {
                hollow.addOccupant(squirrel);

                if (squirrel.carriesBaby) {
                    squirrel.carriesBaby = false;
                    hollow.storeMob(CustomOccupant.create(0, ModEntityTypes.SQUIRREL));
                }
            }
        });
    }

    @Override
    public boolean canUse() {
        if (squirrel.homePos.isPresent() && squirrel.needsToRest) {
            if (squirrel.level().getBlockEntity(squirrel.homePos.get()) instanceof MushroomStemHollowEntity entity) {
                return !entity.isFull() && entity.getBlockPos().closerToCenterThan(squirrel.position(), 2f);
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }
}
