package com.nieslregen;

import com.nieslregen.block.ModBlockEntities;
import com.nieslregen.block.ModBlocks;
import com.nieslregen.block.ModMenuType;
import com.nieslregen.block.ModScreens;
import com.nieslregen.effect.ModEffects;
import com.nieslregen.items.ModItems;
import com.nieslregen.mob.ModEntityTypes;
import com.nieslregen.mob.ModPoiTypes;
import com.nieslregen.tab.ModCreativeTabs;
import com.nieslregen.worldgen.ModFeatures;
import com.nieslregen.worldgen.ModPlacedFeatures;
import com.nieslregen.worldgen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyceliumLeatherMod implements ModInitializer {
	public static final String MOD_ID = "mycelium-leather";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModFeatures.init();
		ModCreativeTabs.registerModCreativeTabs();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.initialize();
		ModMenuType.initialize();
		ModScreens.initialize();
		ModEffects.registerEffects();
		ModEntityTypes.registerModEntityTypes();
		ModWorldGeneration.init();
		ModPoiTypes.init();

		BiomeModifications.addFeature(
				BiomeSelectors.includeByKey(Biomes.MUSHROOM_FIELDS),
				GenerationStep.Decoration.VEGETAL_DECORATION,
				ModPlacedFeatures.HUGE_BLUE_MUSHROOM_WITH_HOLLOW_PLACED_KEY
		);
	}
}