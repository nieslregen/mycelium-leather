package com.nieslregen.worldgen;

import com.nieslregen.mob.ModEntityTypes;
import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;

public class ModEntityGeneration {

    public static void addSpawns() {
        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(Biomes.MUSHROOM_FIELDS),
                MobCategory.CREATURE,
                ModEntityTypes.MYCELIUM_CHICKEN,
                30,
                3,
                5
        );
        SpawnPlacements.register(
                ModEntityTypes.MYCELIUM_CHICKEN,
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MyceliumChicken::checkChickenSpawnRules);
    }
}
