package com.nieslregen.mob;

import com.nieslregen.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public abstract class HollowUser extends SetupAnimal {
    public Optional<BlockPos> homePos = Optional.empty();

    public HollowUser(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    public Optional<BlockPos> getHomePos() {
        if (homePos.isPresent()) {
            BlockState state = this.level().getBlockState(homePos.get());
            if (state.is(ModBlocks.MUSHROOM_STEM_HOLLOW)) {
                return homePos;
            } else {
                homePos = Optional.empty();
            }
        }
        return Optional.empty();
    }
}
