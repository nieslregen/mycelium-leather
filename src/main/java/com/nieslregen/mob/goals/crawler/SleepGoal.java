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
    int delay_fall_asleep;
    int delay_wake_up;
    public static final int MIN_DELAY = 0;
    public static final int MAX_DELAY = 40;

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
                && this.crawler.level().isDarkOutside() || delay_wake_up > 0 ;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.crawler.level().isDarkOutside()) {
            if (--this.delay_fall_asleep <= 0 && !this.crawler.sleeping) {
                this.crawler.sleeping = true;
                this.crawler.setCrawlerState(Crawler.CrawlerState.FALLING_ASLEEP);
            }
        } else {
            this.delay_wake_up -= 1;
        }
    }

    @Override
    public void start() {
        delay_fall_asleep = this.crawler.getRandom().nextInt(MIN_DELAY, MAX_DELAY);
        delay_wake_up = this.crawler.getRandom().nextInt(MIN_DELAY, MAX_DELAY);
        super.start();
        this.crawler.setJumping(false);
        this.crawler.getNavigation().stop();
        this.crawler.getMoveControl().setWantedPosition(this.crawler.getX(), this.crawler.getY(), this.crawler.getZ(), (double)0.0F);
    }

    @Override
    public void stop() {
        super.stop();
        this.crawler.sleeping = false;
        this.crawler.setCrawlerState(Crawler.CrawlerState.WAKING_UP);

        if (!this.crawler.level().isDarkOutside()) { grow(); }
    }
}
