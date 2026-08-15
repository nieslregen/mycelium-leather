package com.nieslregen.mob.goals.crawler;

import com.nieslregen.mob.cawler.Crawler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class SleepGoal extends Goal {
    Crawler crawler;

    public SleepGoal(Crawler crawler) {
        this.crawler = crawler;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }


    public void grow() {
        BlockPos pos = this.crawler.blockPosition().below();

        BlockState blockState = this.crawler.level().getBlockState(pos);
        Holder<Biome> biomeHolder = this.crawler.level().getBiome(pos);

        if (isBeach(biomeHolder)) {
            crawler.setVariant(Crawler.OvergrownType.SALT);
            return;

        }

        if (blockState.is(Blocks.MYCELIUM)) {
            crawler.setVariant(Crawler.OvergrownType.MUSHROOM);
            return;

        }

        if (blockState.is(Blocks.PODZOL)) {
            crawler.setVariant(Crawler.OvergrownType.PODZOL);
            return;

        }

        if (blockState.is(Blocks.GRASS_BLOCK)) {
            crawler.setVariant(Crawler.OvergrownType.MOSS);
        }
    }

    private boolean isBeach(Holder<Biome> biomeHolder) {
        if (biomeHolder instanceof Holder<Biome>) {
            return biomeHolder.is(Biomes.BEACH)
                    || biomeHolder.is(Biomes.SNOWY_BEACH)
                    || biomeHolder.is(Biomes.STONY_SHORE);
        }
        return false;
    }

    @Override
    public boolean canUse() {
        return !crawler.isSleeping()
                && crawler.level().isDarkOutside();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse()
                && this.crawler.sleeping
                && this.crawler.level().isDarkOutside();
    }

    @Override
    public void start() {
        super.start();
        this.crawler.sleeping = true;
//            this.crawler.getNavigation().stop();
//            this.crawler.setSitting(false);
//            this.crawler.setIsCrouching(false);
//            this.crawler.setIsInterested(false);
        this.crawler.setJumping(false);
//            this.crawler.setSleeping(true);
        this.crawler.getNavigation().stop();
        this.crawler.getMoveControl().setWantedPosition(this.crawler.getX(), this.crawler.getY(), this.crawler.getZ(), (double)0.0F);
    }

    @Override
    public void stop() {
        super.stop();
        this.crawler.sleeping = false;
        grow();
    }
}
