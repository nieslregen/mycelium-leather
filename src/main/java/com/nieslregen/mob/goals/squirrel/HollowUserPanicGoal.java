package com.nieslregen.mob.goals.squirrel;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.mob.myceliumsquirrel.MyceliumSquirrel;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class HollowUserPanicGoal extends PanicGoal {
    MyceliumSquirrel squirrel;
    public HollowUserPanicGoal(MyceliumSquirrel mob, double speedModifier) {
        super(mob, speedModifier);
        this.squirrel = mob;
    }


    @Override
    public void start() {
        super.start();
        this.squirrel.wantsToGoHome = true;
    }
}
