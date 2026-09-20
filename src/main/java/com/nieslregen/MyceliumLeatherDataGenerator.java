package com.nieslregen;

import com.nieslregen.datagen.*;
import com.nieslregen.worldgen.ModFeatures;
import com.nieslregen.worldgen.ModPlacedFeatures;
import com.nieslregen.worldgen.WorldGenProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class MyceliumLeatherDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

		var pack = fabricDataGenerator.createPack();

		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModBlockTagsProvider::new);
		pack.addProvider(ModItemTagsProvider::new);
		pack.addProvider(ModEntityLootTableProvider::new);
		pack.addProvider(WorldGenProvider::new);
		pack.addProvider(ModPOITags::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
//
//		registryBuilder.add(
//				Registries.FEATURE_TYPE,
//				ModFeatureTypes::bootstrap
//		);

		registryBuilder.add(
				Registries.FEATURE,
				ModFeatures::bootstrap
		);

		registryBuilder.add(
				Registries.PLACED_FEATURE,
				ModPlacedFeatures::bootstrap
		);

		registryBuilder.add(
				Registries.DAMAGE_TYPE,
				ModDamageTypes::bootstrap
		);
	}
}
