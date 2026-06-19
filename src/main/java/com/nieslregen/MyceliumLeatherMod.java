package com.nieslregen;

import com.nieslregen.block.ModBlockEntities;
import com.nieslregen.block.ModBlocks;
import com.nieslregen.block.ModMenuType;
import com.nieslregen.block.ModScreens;
import com.nieslregen.effect.ModEffects;
import com.nieslregen.items.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyceliumLeatherMod implements ModInitializer {
	public static final String MOD_ID = "mycelium-leather";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.initialize();
		ModMenuType.initialize();
		ModScreens.initialize();
		ModEffects.registerEffects();

	}
}