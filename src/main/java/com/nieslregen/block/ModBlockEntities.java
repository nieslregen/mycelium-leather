package com.nieslregen.block;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.block.custom.charcoalpile.CharCoalPileEntity;
import com.nieslregen.block.custom.herbariumpress.HerbariumPressEntity;
import com.nieslregen.block.custom.mushroomstem.MushroomStemHollowEntity;
import com.nieslregen.block.custom.mushroomstem.ScratchedMushroomStemEntity;
import com.nieslregen.block.custom.tinycauldron.TinyCauldronEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;


public class ModBlockEntities {

    public static final BlockEntityType<HerbariumPressEntity> HERBARIUM_PRESS_ENTITY = register("herbarium_press", HerbariumPressEntity::new, ModBlocks.HERBARIUM_PRESS);
    public static final BlockEntityType<TinyCauldronEntity> TINY_CAULDRON_ENTITY = register("tiny_cauldron", TinyCauldronEntity::new,  ModBlocks.TINY_CAULDRON);
    public static final BlockEntityType<CharCoalPileEntity> CHARCOAL_PILE_ENTITY = register("soot_trap_furnace", CharCoalPileEntity::new, ModBlocks.CHARCOAL_PILE);

    public static final BlockEntityType<MushroomStemHollowEntity> MUSHROOM_STEM_HOLLOW_ENTITY = register("mushroom_stem_hollow", MushroomStemHollowEntity::new, ModBlocks.MUSHROOM_STEM_HOLLOW);
    public static final BlockEntityType<ScratchedMushroomStemEntity> SCRATCHED_MUSHROOM_STEM_ENTITY = register("scratched_mushroom_stem", ScratchedMushroomStemEntity::new, ModBlocks.SCRATCHED_MUSHROOM_STEM);

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        Identifier id = Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name);
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static void initialize() { }
}