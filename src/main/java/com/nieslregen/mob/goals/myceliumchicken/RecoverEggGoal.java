package com.nieslregen.mob.goals.myceliumchicken;

import com.nieslregen.items.ModItems;
import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import com.nieslregen.mob.myceliumchicken.MyceliumChickenNestEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.List;

public class RecoverEggGoal extends Goal {
    private final MyceliumChicken chicken;

    public RecoverEggGoal(MyceliumChicken chicken) {
        this.chicken = chicken;
    }

    @Override
    public void start() {
        super.start();
        List<ItemEntity> items = getNearbyItems();
        if (!items.isEmpty()) {
            chicken.getNavigation().moveTo(items.getFirst(), 1.2F);
        }
    }

    private List<ItemEntity> getNearbyItems() {
        return chicken
                .level().getEntitiesOfClass(
                        ItemEntity.class,
                        chicken.getBoundingBox().inflate(8.0f, 8.0f, 8.0f),
                        (e) -> e.getItem().is(ModItems.getResourceKey(ModItems.MYCELIUM_CHICKEN_EGG)) && e.isAlive()
                );
    }

    @Override
    public boolean canUse() {
        return shouldLocateAndPickUpEgg();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && shouldLocateAndPickUpEgg();
    }

    public boolean shouldLocateAndPickUpEgg() {
        if (chicken.getNestPos().isPresent()) {
            BlockPos nest = this.chicken.getNestPos().get();

            if (chicken.level().getBlockEntity(nest) instanceof MyceliumChickenNestEntity nestEntity) {
                return !nestEntity.hasEgg(chicken.level().getBlockState(nest)) && !chicken.carriesStolenEgg;
            }
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        List<ItemEntity> items = getNearbyItems();
        if (!items.isEmpty()) {
            chicken.getNavigation().moveTo(items.getFirst(), 1.2D);
            if (chicken.blockPosition().closerToCenterThan(items.getFirst().position(), 1.5F)) {
                chicken.carriesStolenEgg = true;

                items.getFirst().getItem().shrink(1);
            }
        }
    }
}
