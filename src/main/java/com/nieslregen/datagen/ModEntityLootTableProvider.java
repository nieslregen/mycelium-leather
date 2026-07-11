package com.nieslregen.datagen;

import com.nieslregen.MyceliumLeatherMod;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModEntityLootTableProvider extends SimpleFabricLootTableSubProvider {


    public static ResourceKey<LootTable> MYCELIUM_CHICKEN_DROP = ResourceKey
            .create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "mycelium_chicken/feather_drop"));

    public ModEntityLootTableProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture, LootContextParamSets.ENTITY);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
         output.accept(MYCELIUM_CHICKEN_DROP, LootTable.lootTable()
                 .withPool(LootPool.lootPool()
                         .add(LootItem.lootTableItem(Items.FEATHER)
                                 .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f))))));

        output.accept(BuiltInLootTables.CHICKEN_LAY, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.FEATHER)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f))))));
    }
}
