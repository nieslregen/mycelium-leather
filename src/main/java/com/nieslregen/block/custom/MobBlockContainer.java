package com.nieslregen.block.custom;

import com.nieslregen.mob.CustomOccupant;
import com.nieslregen.mob.CustomOccupantData;
import com.nieslregen.mob.HollowUser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class MobBlockContainer extends BlockEntity {

    private final List<CustomOccupantData> storedOccupants = new ArrayList<>();
    public static final int MAX_OCCUPANTS = 3;
    public final EntityType<?> occupantType;

    public MobBlockContainer(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState, EntityType<?> occupantTyp) {
        super(type, worldPosition, blockState);
        this.occupantType = occupantTyp;
    }

    public void setChanged() {
        if (this.isFireNearby()) {
            List<Entity> released = new ArrayList<>();
            storedOccupants.forEach(
                    occupantData -> releaseOccupant(level, worldPosition, getBlockState(), occupantData.toOccupant(), released)
            );
        }

        super.setChanged();
    }

    public boolean isFireNearby() {
        if (this.level == null) {
            return false;
        } else {
            for(BlockPos pos : BlockPos.betweenClosed(this.worldPosition.offset(-1, -1, -1), this.worldPosition.offset(1, 1, 1))) {
                if (this.level.getBlockState(pos).getBlock() instanceof FireBlock) {
                    return true;
                }
            }
            return false;
        }
    }

    @VisibleForDebug
    public int getOccupantCount() {
        return this.storedOccupants.size();
    }


    public void addOccupant(final LivingEntity mob) {
        if (this.storedOccupants.size() < MAX_OCCUPANTS) {
            mob.stopRiding();
            mob.ejectPassengers();
            // Needs Leashable interface
//            mob.dropLeash();
            this.storeMob(CustomOccupant.of(mob));
            if (this.level != null) {
                BlockPos blockPos = this.getBlockPos();
                this.level.playSound(
                        (Entity)null,
                        (double)blockPos.getX(),
                        (double)blockPos.getY(),
                        (double)blockPos.getZ(),
                        SoundEvents.BEEHIVE_ENTER,
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F
                );
                this.level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(mob, this.getBlockState()));
            }

            mob.discard();
            super.setChanged();
        }
    }


    public void storeMob(final CustomOccupant occupant) {
        this.storedOccupants.add(new CustomOccupantData(occupant));
    }

    private static boolean releaseOccupant(final Level level, final BlockPos blockPos, final BlockState state, final CustomOccupant occupantData, final @Nullable List<Entity> spawned) {
        // ToDo
//        Direction facing = (Direction)state.getValue(BeehiveBlock.FACING);
        Direction facing = Direction.NORTH;
        BlockPos facingPos = blockPos.relative(facing);
        boolean frontBlocked = !level.getBlockState(facingPos).getCollisionShape(level, facingPos).isEmpty();

        if (frontBlocked) { return false; }

        MobBlockContainer container = (MobBlockContainer) level.getBlockEntity(blockPos);
        Entity entity = occupantData.createEntity(level, blockPos, container.occupantType);
        if (entity == null ) { return false; }

        if (spawned != null) {
            spawned.add(entity);
            if (entity instanceof HollowUser h) {
                h.homePos = Optional.of(blockPos);
            }
        }

        float bbWidth = entity.getBbWidth();
        double delta = frontBlocked ? (double)0.0F : 0.55 + (double)(bbWidth / 2.0F);
        double spawnX = (double)blockPos.getX() + (double)0.5F + delta * (double)facing.getStepX();
        double spawnY = (double)blockPos.getY() + (double)0.5F - (double)(entity.getBbHeight() / 2.0F);
        double spawnZ = (double)blockPos.getZ() + (double)0.5F + delta * (double)facing.getStepZ();
        entity.snapTo(spawnX, spawnY, spawnZ, entity.getYRot(), entity.getXRot());


        level.playSound((Entity)null, blockPos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(entity, level.getBlockState(blockPos)));
        return level.addFreshEntity(entity);


    }

    private static void tickOccupants(final Level level, final BlockPos pos, final BlockState state, final List<CustomOccupantData> stored) {
        boolean changed = false;
        Iterator<CustomOccupantData> iterator = stored.iterator();

        while(iterator.hasNext()) {
            CustomOccupantData data = (CustomOccupantData)iterator.next();
            if (data.tick()) {
//                BeehiveBlockEntity.BeeReleaseStatus releaseStatus = data.hasNectar() ? BeehiveBlockEntity.BeeReleaseStatus.HONEY_DELIVERED : BeehiveBlockEntity.BeeReleaseStatus.BEE_RELEASED;
                if (releaseOccupant(level, pos, state, data.toOccupant(), (List)null)) {
                    changed = true;
                    iterator.remove();
                }
            }
        }

        if (changed) {
            setChanged(level, pos, state);
        }

    }

    public static void serverTick(final Level level, final BlockPos blockPos, final BlockState state, final MobBlockContainer entity) {
        tickOccupants(level, blockPos, state, entity.storedOccupants);
        if (!entity.storedOccupants.isEmpty() && level.getRandom().nextDouble() < 0.005) {
            double x = (double)blockPos.getX() + (double)0.5F;
            double y = (double)blockPos.getY();
            double z = (double)blockPos.getZ() + (double)0.5F;
            level.playSound((Entity)null, x, y, z, SoundEvents.BEEHIVE_WORK, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

    }

    protected void loadAdditional(final ValueInput input) {
        super.loadAdditional(input);
        this.storedOccupants.clear();
        input.read(
                    "occupants",
                    CustomOccupant.LIST_CODEC)
            .orElse(List.of())
            .forEach(this::storeMob);
    }

    protected void saveAdditional(final ValueOutput output) {
        super.saveAdditional(output);
        output.store("occupants", CustomOccupant.LIST_CODEC, this.getMobs());
    }

    public boolean isFull() {
        return this.storedOccupants.size() >= 3;
    }

    public List<CustomOccupant> getMobs() {
        return this.storedOccupants.stream().map(CustomOccupantData::toOccupant).toList();
    }



}
