package com.nieslregen.mob.cawler;

import com.mojang.serialization.Codec;
import com.nieslregen.datagen.ModEntityLootTableProvider;
import com.nieslregen.mob.ModEntityTypes;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jspecify.annotations.Nullable;

import java.util.function.IntFunction;

public class Crawler extends Animal implements Shearable {

    private static final EntityDataAccessor<Integer> DATA_TYPE;

    private int timeUntilResting = 0;
    private boolean needsToRest = false;

    public Crawler(EntityType<? extends Animal> type, Level level) {
        super(type, level);
        resetTimeUntilResting();
    }

    private void resetTimeUntilResting() {
        timeUntilResting = this.random.nextInt(6000) + 6000;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(DATA_TYPE, OvergrownType.DEFAULT.id);
    }

    private void setVariant(final OvergrownType variant) {
        this.entityData.set(DATA_TYPE, variant.id);
    }

    public OvergrownType getVariant() {
        return OvergrownType.byId((Integer)this.entityData.get(DATA_TYPE));
    }

    static {
        DATA_TYPE = SynchedEntityData.defineId(Crawler.class, EntityDataSerializers.INT);
//        BABY_DIMENSIONS = EntityDimensions.scalable(0.45F, 0.7F).withEyeHeight(0.69F).withAttachments(EntityAttachments.builder().attach(EntityAttachment.PASSENGER, 0.0F, 0.75F, 0.0F));
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return (AgeableMob) ModEntityTypes.CRAWLER.create(level, EntitySpawnReason.BREEDING);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5)
                .add(Attributes.TEMPT_RANGE, 10)
                .add(Attributes.MOVEMENT_SPEED, .1)
                .add(Attributes.FOLLOW_RANGE, 25)
                .add(Attributes.SCALE, 2.0);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, (double)1.25F));
        this.goalSelector.addGoal(2, new EatBlockGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this,1));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.SHEARS) && this.level() instanceof ServerLevel serverLevel) {
            if (readyForShearing()) {
                this.shear(serverLevel, SoundSource.PLAYERS, itemStack);
                this.gameEvent(GameEvent.SHEAR, player);
                itemStack.hurtAndBreak(1, player, hand.asEquipmentSlot());
                return InteractionResult.SUCCESS_SERVER;
            }
            return InteractionResult.CONSUME;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level() instanceof ServerLevel serverLevel && !needsToRest) {
            if (this.isAlive() && --timeUntilResting <= 0) {
                needsToRest = true;
            }
        }
    }

    @Override
    public void shear(ServerLevel level, SoundSource soundSource, ItemStack tool) {
        if (!isOvergrown()) { return; }

        ResourceKey<LootTable> loot;
        switch (getVariant()) {
            case SALT -> loot = ModEntityLootTableProvider.CRAWLER_SALT_DROP;
            case PODZOL -> loot = ModEntityLootTableProvider.CRAWLER_PODZOL_DROP;
            case MUSHROOM -> loot = ModEntityLootTableProvider.CRAWLER_MUSHROOM_DROP;
            default -> loot = ModEntityLootTableProvider.CRAWLER_MOSS_DROP;
        }

        level.playSound((Entity)null, this, SoundEvents.SHEEP_SHEAR, soundSource, 1.0F, 1.0F);
        this.dropFromShearingLootTable(level, loot, tool, (l, drop) -> {
            for(int i = 0; i < drop.getCount(); ++i) {
                ItemEntity entity = this.spawnAtLocation(l, drop.copyWithCount(1), 1.0F);
                if (entity != null) {
                    entity.setDeltaMovement(entity.getDeltaMovement().add((double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F), (double)(this.random.nextFloat() * 0.05F), (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)));
                }
            }

        });
        setVariant(OvergrownType.NONE);
//        this.setSheared(true);
    }

    @Override
    public boolean readyForShearing() {
        return isOvergrown();
    }

    public OvergrownType getOvergrownSurface() {
        return getVariant();
    }

    public boolean isOvergrown() {
        return getVariant() != OvergrownType.NONE;
    }

    public static boolean checkCrawlerSpawnRules(EntityType<Crawler> crawlerEntityType, ServerLevelAccessor serverLevelAccessor, EntitySpawnReason entitySpawnReason, BlockPos blockPos, RandomSource randomSource) {
        return serverLevelAccessor.getBlockState(blockPos.below()).is(BlockTags.MOOSHROOMS_SPAWNABLE_ON) && isBrightEnoughToSpawn(serverLevelAccessor, blockPos);
    }

    private class EatBlockGoal extends Goal {
        Crawler crawler;
        private int restingTime = 0;

        public EatBlockGoal(Crawler crawler) {
            this.crawler = crawler;
        }

        @Override
        public void tick() {
            super.tick();
            restingTime++;


        }

        public void grow() {
            BlockPos pos = this.crawler.blockPosition().below();

            BlockState blockState = this.crawler.level().getBlockState(pos);
            Holder<Biome> biomeHolder = this.crawler.level().getBiome(pos);

            if (isBeach(biomeHolder)) {
                setVariant(OvergrownType.SALT);
                return;

            }

            if (blockState.is(Blocks.MYCELIUM)) {
                setVariant(OvergrownType.MUSHROOM);
                return;

            }

            if (blockState.is(Blocks.PODZOL)) {
                setVariant(OvergrownType.PODZOL);
                return;

            }

            if (blockState.is(Blocks.GRASS_BLOCK)) {
                setVariant(OvergrownType.MOSS);
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
            return this.crawler.needsToRest;
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.crawler.needsToRest && this.restingTime <= 20 * 60 * 3;
        }

        @Override
        public void start() {
            super.start();
            this.crawler.moveControl.setWait();
        }

        @Override
        public void stop() {
            super.stop();
            this.crawler.needsToRest = false;
            this.crawler.resetTimeUntilResting();
            restingTime = 0;
            grow();
        }
    }

    public enum OvergrownType implements StringRepresentable {
        NONE("none", 0),
        MUSHROOM("mushroom", 1),
        SALT("salt", 2),
        MOSS("moss", 3),
        PODZOL("podzol", 4);

        public static final OvergrownType DEFAULT = NONE;
        public static final Codec<OvergrownType> CODEC = StringRepresentable.fromEnum(OvergrownType::values);
        private static final IntFunction<OvergrownType> BY_ID = ByIdMap.continuous(OvergrownType::id, values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
        public static final StreamCodec<ByteBuf, OvergrownType> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, OvergrownType::id);
        private final String type;
        private final int id;

        private OvergrownType(final String type, final int id) {
            this.type = type;
            this.id = id;
        }

        public String getSerializedName() {
            return this.type;
        }

        private int id() {
            return this.id;
        }

        private static OvergrownType byId(final int id) {
            return BY_ID.apply(id);
        }
    }
}