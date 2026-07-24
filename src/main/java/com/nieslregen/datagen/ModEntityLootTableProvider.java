package com.nieslregen.datagen;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
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

    public static ResourceKey<LootTable> CRAWLER_MUSHROOM_DROP = ResourceKey
            .create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "crawler/mushroom_drop"));

    public static ResourceKey<LootTable> CRAWLER_SALT_DROP = ResourceKey
            .create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "crawler/salt_drop"));

    public static ResourceKey<LootTable> CRAWLER_MOSS_DROP = ResourceKey
            .create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "crawler/moss_drop"));

    public static ResourceKey<LootTable> CRAWLER_PODZOL_DROP = ResourceKey
            .create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "crawler/podzol_drop"));

    public ModEntityLootTableProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture, LootContextParamSets.ENTITY);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(MYCELIUM_CHICKEN_DROP, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.FEATHER)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f))))
                        .setRolls(ConstantValue.exactly(1))));

        output.accept(CRAWLER_SALT_DROP, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.SALT)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0f))))
                        .setRolls(ConstantValue.exactly(1))));


        output.accept(CRAWLER_MOSS_DROP, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.MOSS_CARPET)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0f))))
                        .add(LootItem.lootTableItem(Items.SHORT_GRASS)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0f))))
                        .add(LootItem.lootTableItem(Items.TALL_GRASS)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f))))
                        .setRolls(ConstantValue.exactly(1))));

        output.accept(CRAWLER_PODZOL_DROP, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0f))))
                        .setRolls(ConstantValue.exactly(1))));

        output.accept(CRAWLER_MUSHROOM_DROP, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.MYCELIUM_PATCH)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f))))
                        .add(LootItem.lootTableItem(Items.RED_MUSHROOM)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f))))
                        .add(LootItem.lootTableItem(Items.BROWN_MUSHROOM)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f))))
                        .setRolls(ConstantValue.exactly(1))));



    }
}
