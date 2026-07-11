package com.nieslregen;

import com.nieslregen.block.ModBlockEntities;
import com.nieslregen.block.ModBlocks;
import com.nieslregen.block.ModMenuType;
import com.nieslregen.block.ModScreens;
import com.nieslregen.effect.ModEffects;
import com.nieslregen.items.ModItems;
import com.nieslregen.mob.ModEntityTypes;
import com.nieslregen.tab.ModCreativeTabs;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyceliumLeatherMod implements ModInitializer {
	public static final String MOD_ID = "mycelium-leather";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModCreativeTabs.registerModCreativeTabs();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.initialize();
		ModMenuType.initialize();
		ModScreens.initialize();
		ModEffects.registerEffects();
		ModEntityTypes.registerModEntityTypes();

		modifyLootTables();
	}

	private void modifyLootTables() {
		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			if (key.equals(BuiltInLootTables.CHICKEN_LAY)) {
				tableBuilder.withPool(
						LootPool.lootPool()
								.add(LootItem.lootTableItem(Items.FEATHER))
				);
			}
		});
	}
}