package com.nieslregen.mob.goals.myceliumchicken;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import com.nieslregen.mob.myceliumchicken.MyceliumChickenNestEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DefendNestGoal extends MeleeAttackGoal {
    private final MyceliumChicken chicken;

    public DefendNestGoal(MyceliumChicken chicken) {
        super(chicken, 1.25f, true);
        this.chicken = chicken;
    }

    @Override
    public boolean canUse() {
        return chicken.isNestThreatened(5);
    }

    @Override
    public void start() {
        super.start();
        MyceliumLeatherMod.LOGGER.info("DefendNestGoal starting...");
        chicken.setTarget(chicken.nearbyMobs.getFirst());
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse()
                && chicken.isNestThreatened(5)
                && chicken.getTarget() != null && chicken.getTarget().isAlive();
    }




    @Override
    public void stop() {
        super.stop();
        MyceliumLeatherMod.LOGGER.info("DefendNestGoal stop...");
        chicken.setTarget(null);
        chicken.stopBeingAngry();
    }
}
