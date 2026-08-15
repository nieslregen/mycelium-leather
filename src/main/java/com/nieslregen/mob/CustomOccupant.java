package com.nieslregen.mob;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nieslregen.MyceliumLeatherMod;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityProcessor;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.TagValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record CustomOccupant(TypedEntityData<EntityType<?>> entityData, int ticksInContainer, int minTicksInContainer) {

    public static final Codec<CustomOccupant> CODEC = RecordCodecBuilder.create((i) -> i.group(
            TypedEntityData.codec(EntityType.CODEC)
                    .fieldOf("entity_data")
                    .forGetter(CustomOccupant::entityData),
            Codec.INT.fieldOf("ticks_in_container")
                    .forGetter(CustomOccupant::ticksInContainer),
            Codec.INT.fieldOf("min_ticks_in_container")
                    .forGetter(CustomOccupant::minTicksInContainer))
            .apply(i, CustomOccupant::new));

    public static final Codec<List<CustomOccupant>> LIST_CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf,CustomOccupant> STREAM_CODEC;

    public static CustomOccupant of(final Entity entity) {
        CustomOccupant occupant;
        try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(entity.problemPath(), MyceliumLeatherMod.LOGGER)) {
            TagValueOutput output = TagValueOutput.createWithContext(reporter, entity.registryAccess());
            entity.save(output);
            Objects.requireNonNull(output);
            CompoundTag entityTag = output.buildResult();
            occupant = new CustomOccupant(TypedEntityData.of(entity.getType(), entityTag), 0, 2400);
        }

        return occupant;
    }

    public static CustomOccupant create(final int ticksInHive, EntityType<?> type) {
        return new CustomOccupant(TypedEntityData.of(type, new CompoundTag()), ticksInHive, 600);
    }

    public @Nullable Entity createEntity(final Level level, final BlockPos homePos, EntityType<?> type) {
        CompoundTag entityTag = this.entityData.copyTagWithoutId();
        Objects.requireNonNull(entityTag);
        Entity entity = EntityType.loadEntityRecursive((EntityType)this.entityData.type(), entityTag, level, EntitySpawnReason.LOAD, EntityProcessor.NOP);
        if (entity != null) {
//            entity.setNoGravity(true);
            if (entity.is(type)) {
                HollowUser occupant = (HollowUser) entity;
                occupant.homePos = Optional.of(homePos);
                setOccuoantReleaseData(this.ticksInContainer, occupant);
            }

            return entity;
        } else {
            return null;
        }
    }

    private static void setOccuoantReleaseData(final int ticksInHive, final HollowUser hollowUser) {
        updateOccupantAge(ticksInHive, hollowUser);
        hollowUser.setInLoveTime(Math.max(0, hollowUser.getInLoveTime() - ticksInHive));
    }

    private static void updateOccupantAge(final int ticksInHive, final HollowUser hollowUse) {
        if (!hollowUse.isAgeLocked()) {
            int age = hollowUse.getAge();
            if (age < 0) {
                hollowUse.setAge(Math.min(0, age + ticksInHive));
            } else if (age > 0) {
                hollowUse.setAge(Math.max(0, age - ticksInHive));
            }

        }
    }

    static {
        LIST_CODEC = CODEC.listOf();
        STREAM_CODEC = StreamCodec.composite(
                TypedEntityData.streamCodec(EntityType.STREAM_CODEC),
                CustomOccupant::entityData,
                ByteBufCodecs.VAR_INT,
                CustomOccupant::ticksInContainer,
                ByteBufCodecs.VAR_INT,
                CustomOccupant::minTicksInContainer,
                CustomOccupant::new);
    }

}
