package com.nieslregen.mob;

import com.nieslregen.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.Optional;

public abstract class HollowUser extends ModAnimal {
    public Optional<BlockPos> homePos = Optional.empty();

    public static final String HOME_POSITION_IDENTIFIER = "homePos";
    public static final String HOME_POSITION_X = "homePosX";
    public static final String HOME_POSITION_Y = "homePosY";
    public static final String HOME_POSITION_Z = "homePosZ";

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

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        if (homePos.isPresent()) {
            output.putBoolean(HOME_POSITION_IDENTIFIER, true);
            output.putInt(HOME_POSITION_X, homePos.get().getX());
            output.putInt(HOME_POSITION_Y, homePos.get().getY());
            output.putInt(HOME_POSITION_Z, homePos.get().getZ());
        } else {
            output.putBoolean(HOME_POSITION_IDENTIFIER, false);
        }
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        if (input.getBooleanOr(HOME_POSITION_IDENTIFIER, false)) {
            homePos = Optional.of(new BlockPos(
                    input.getIntOr(HOME_POSITION_X, 0),
                    input.getIntOr(HOME_POSITION_Y, 0),
                    input.getIntOr(HOME_POSITION_Z, 0)
            ));
        }
    }
}
