package com.nieslregen.mob.goals.squirrel;

import com.nieslregen.block.custom.mushroomstem.MushroomStemHollowEntity;
import com.nieslregen.mob.myceliumsquirrel.MyceliumSquirrel;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.AgeableMob;

import java.util.Optional;

public class SquirrelBreedGoal extends net.minecraft.world.entity.ai.goal.BreedGoal {
    private final MyceliumSquirrel squirrel;

    public SquirrelBreedGoal(MyceliumSquirrel animal, double speedModifier) {
        super(animal, speedModifier);
        squirrel = animal;
    }

    @Override
    public void start() {
        super.start();
        this.squirrel.needsToRest = true;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && squirrel.homePos.isPresent();
    }

    @Override
    protected void breed() {

        // standard behavior of livable mob:

        ServerPlayer loveCause = this.animal.getLoveCause();
        if (loveCause == null && this.partner.getLoveCause() != null) {
            loveCause = this.partner.getLoveCause();
        }

        if (loveCause != null) {
            loveCause.awardStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(loveCause, this.animal, this.partner, (AgeableMob)null);
        }
        this.animal.setAge(6000);
        this.partner.setAge(6000);
        this.animal.resetLove();
        this.partner.resetLove();

        // custom squirrel behavior


        MyceliumSquirrel p = (MyceliumSquirrel) partner;

        if (squirrel.homePos.isPresent() && p.homePos.isPresent()) {

            BlockPos sPos = squirrel.homePos.get();
            BlockPos pPos = p.homePos.get();

            Optional<MushroomStemHollowEntity> sHollow = Optional.ofNullable((MushroomStemHollowEntity) level.getBlockEntity(sPos));
            Optional<MushroomStemHollowEntity> pHollow = Optional.ofNullable((MushroomStemHollowEntity) level.getBlockEntity(pPos));


            if (sHollow.isPresent() && pHollow.isPresent()) {
                if (sPos.equals(pPos) && sHollow.get().getOccupantCount() < 3) {
                    createBaby(p, sHollow.get());
                    return;
                }

                if (sHollow.get().getOccupantCount() < 2) {
                    createBaby(p, sHollow.get());
                    p.homePos = Optional.of(sPos);
                    return;
                }

                if (pHollow.get().getOccupantCount() < 2) {
                    createBaby(p, pHollow.get());
                    squirrel.homePos = Optional.of(pPos);
                }
            }
        }
    }

    private void createBaby(MyceliumSquirrel p, MushroomStemHollowEntity hollow) {
        squirrel.needsToRest = true;
        squirrel.carriesBaby = true;
        squirrel.timeUntilResting = 0;
        p.timeUntilResting = 0;
        p.needsToRest = true;
    }
}
