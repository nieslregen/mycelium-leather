package com.nieslregen.datagen;

import com.nieslregen.block.ModBlocks;
import com.nieslregen.block.custom.charcoalpile.CharCoalPileEntity;
import com.nieslregen.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModLootTableProvider extends FabricBlockLootSubProvider {
    public ModLootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    public void generate() {
        dropSelf(ModBlocks.HERBARIUM_PRESS);
        dropSelf(ModBlocks.TINY_CAULDRON);
    }

    @Override
    public BlockLootSubProvider withConditions(ResourceCondition... conditions) {
        return super.withConditions(conditions);
    }

    @Override
    public BiConsumer<ResourceKey<LootTable>, LootTable.Builder> withConditions(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> exporter, ResourceCondition... conditions) {
        return super.withConditions(exporter, conditions);
    }
}
