package com.nieslregen.mob.goals.myceliumchicken;

import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.gamerules.GameRules;

public class BreedGoal extends net.minecraft.world.entity.ai.goal.BreedGoal {
    private final MyceliumChicken thisEntity;

    public BreedGoal(final MyceliumChicken chicken, double speedModifier) {
        super(chicken, speedModifier);
        this.thisEntity = chicken;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !this.thisEntity.carriesEgg();
    }

    @Override
    protected void breed() {
        ServerPlayer loveCause = this.animal.getLoveCause();
        if (loveCause == null && this.partner.getLoveCause() != null) {
            loveCause = this.partner.getLoveCause();
        }

        if (loveCause != null) {
            loveCause.awardStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(loveCause, this.animal, this.partner, (AgeableMob)null);
        }
        this.thisEntity.setCarriesEgg(true);
        this.animal.setAge(6000);
        this.partner.setAge(6000);
        this.animal.resetLove();
        this.partner.resetLove();
        RandomSource random = this.animal.getRandom();
        if ((Boolean)getServerLevel(this.level).getGameRules().get(GameRules.MOB_DROPS)) {
            this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
        }

    }
}
