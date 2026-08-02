package com.nieslregen.mob;

import com.nieslregen.block.ModBlocks;
import com.nieslregen.MyceliumLeatherMod;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public class ModPoiTypes {


    public static final ResourceKey<PoiType> SQUIRREL_HOME =
            createKey("squirrel_home");

    public static void bootstrap(BootstrapContext<PoiType> context) {
        context.register(
                SQUIRREL_HOME,
                new PoiType(
                        getBlockStates(ModBlocks.MUSHROOM_STEM_HOLLOW),
                        0,
                        1
                )
        );
    }

    private static ResourceKey<PoiType> createKey(String name) {
        return ResourceKey.create(
                Registries.POINT_OF_INTEREST_TYPE,
                Identifier.fromNamespaceAndPath(
                        MyceliumLeatherMod.MOD_ID,
                        name
                )
        );
    }

    private static Set<BlockState> getBlockStates(Block block) {
        return Set.copyOf(
                block.getStateDefinition().getPossibleStates()
        );
    }
}