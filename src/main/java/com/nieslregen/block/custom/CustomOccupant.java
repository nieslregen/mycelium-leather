package com.nieslregen.block.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nieslregen.MyceliumLeatherMod;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.storage.TagValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;

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
        CustomOccupant var5;
        try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(entity.problemPath(), MyceliumLeatherMod.LOGGER)) {
            TagValueOutput output = TagValueOutput.createWithContext(reporter, entity.registryAccess());
            entity.save(output);
//            List var10000 = BeehiveBlockEntity.IGNORED_BEE_TAGS;
            Objects.requireNonNull(output);
//            var10000.forEach(output::discard);
            CompoundTag entityTag = output.buildResult();
            boolean hasNectar = entityTag.getBooleanOr("HasNectar", false);
            var5 = new CustomOccupant(TypedEntityData.of(entity.getType(), entityTag), 0, hasNectar ? 2400 : 600);
        }

        return var5;
    }

    public static BeehiveBlockEntity.Occupant create(final int ticksInHive) {
        return new BeehiveBlockEntity.Occupant(TypedEntityData.of(EntityTypes.BEE, new CompoundTag()), ticksInHive, 600);
    }

    public @Nullable Entity createEntity(final Level level, final BlockPos hivePos) {
        CompoundTag entityTag = this.entityData.copyTagWithoutId();
//        List var10000 = BeehiveBlockEntity.IGNORED_BEE_TAGS;
        Objects.requireNonNull(entityTag);
//        var10000.forEach(entityTag::remove);
        Entity entity = EntityType.loadEntityRecursive((EntityType)this.entityData.type(), entityTag, level, EntitySpawnReason.LOAD, EntityProcessor.NOP);
        if (entity != null && entity.is(EntityTypeTags.BEEHIVE_INHABITORS)) {
            entity.setNoGravity(true);
            if (entity instanceof Bee) {
                Bee bee = (Bee)entity;
                bee.setHivePos(hivePos);
                setBeeReleaseData(this.ticksInContainer, bee);
            }

            return entity;
        } else {
            return null;
        }
    }

    private static void setBeeReleaseData(final int ticksInHive, final Bee bee) {
        updateBeeAge(ticksInHive, bee);
        bee.setInLoveTime(Math.max(0, bee.getInLoveTime() - ticksInHive));
    }

    private static void updateBeeAge(final int ticksInHive, final Bee bee) {
        if (!bee.isAgeLocked()) {
            int age = bee.getAge();
            if (age < 0) {
                bee.setAge(Math.min(0, age + ticksInHive));
            } else if (age > 0) {
                bee.setAge(Math.max(0, age - ticksInHive));
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
