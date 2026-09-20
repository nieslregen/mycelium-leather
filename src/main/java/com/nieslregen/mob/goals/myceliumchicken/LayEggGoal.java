package com.nieslregen.mob.goals.myceliumchicken;

import com.nieslregen.block.ModBlocks;
import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import com.nieslregen.mob.myceliumchicken.MyceliumChickenNestBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Optional;

public class LayEggGoal extends MoveToBlockGoal {
    private final MyceliumChicken chicken;

    public LayEggGoal(final MyceliumChicken chicken, double speedModifier) {
        super(chicken, speedModifier, 16);
        this.chicken = chicken;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public boolean canUse() {
        return this.chicken.carriesEgg() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }

    @Override
    public double acceptedDistance() {
        return 2;
    }

    @Override
    public void tick() {
        super.tick();
        BlockPos chickenPos = this.chicken.blockPosition();
        if (this.isReachedTarget()) {
            Level level = this.chicken.level();
            level.playSound((Entity) null, chickenPos, SoundEvents.CHICKEN_EGG, SoundSource.BLOCKS, 0.3F, 0.9F + level.getRandom().nextFloat() * 0.2F);

            BlockPos eggPos = this.blockPos.above();
            BlockState eggState = (BlockState) ModBlocks.MYCELIUM_CHICKEN_NEST.defaultBlockState().setValue(com.nieslregen.mob.myceliumchicken.MyceliumChickenNestBlock.HAS_EGG, true);
            level.setBlock(eggPos, eggState, Block.UPDATE_ALL);
            level.gameEvent(GameEvent.BLOCK_PLACE, eggPos, GameEvent.Context.of(this.chicken, eggState));

            this.chicken.setCarriesEgg(false);
            this.chicken.setInLoveTime(600);
            this.chicken.nestPos = Optional.of(eggPos);
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        return level.isEmptyBlock(pos.above()) && MyceliumChickenNestBlock.isMycelium(level, pos);
    }
}
