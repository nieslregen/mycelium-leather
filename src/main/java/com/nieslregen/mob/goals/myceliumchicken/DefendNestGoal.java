package com.nieslregen.mob.goals.myceliumchicken;

import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

import java.util.List;

public class DefendNestGoal extends MeleeAttackGoal {
    private final MyceliumChicken chicken;

    public DefendNestGoal(MyceliumChicken chicken) {
        super(chicken, 1.5f, false);
        this.chicken = chicken;
    }

    @Override
    public boolean canUse() {
        if (!chicken.nestPos.isPresent()) {
            return false;
        }
        List<LivingEntity> targets = chicken.getTargets(this.chicken, 3);

        if (!targets.isEmpty()) {
            chicken.setTarget(targets.getFirst());
            return super.canUse()
                    && !chicken.carriesStolenEgg;
        }
        return false;
    }


    @Override
    public boolean canContinueToUse() {
        return chicken.getTarget() != null
                && chicken.getTarget().isAlive()
                && chicken.getTargets(this.chicken, 3).contains(chicken.getTarget())
                && !chicken.carriesStolenEgg
                && chicken.nestPos.isPresent()
                && super.canContinueToUse();
    }


    @Override
    public void stop() {
        super.stop();
        chicken.setTarget(null);
        chicken.stopBeingAngry();
    }
}
