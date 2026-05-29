package com.nieslregen.block;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.block.herbariumpress.HerbariumPressEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;


public class ModBlockEntities {

    public static final BlockEntityType<HerbariumPressEntity> HERBARIUM_PRESS_ENTITY = register("herbarium_press", HerbariumPressEntity::new, ModBlocks.HERBARIUM_PRESS);

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