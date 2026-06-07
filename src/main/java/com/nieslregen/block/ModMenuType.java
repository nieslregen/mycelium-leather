package com.nieslregen.block;

import com.nieslregen.block.custom.herbariumpress.HerbariumPressMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;


public class ModMenuType {
    public static final MenuType<@NotNull HerbariumPressMenu> HERBARIUM_PRESS_MENU_TYPE = register("herbarium-press", HerbariumPressMenu::new);

    public static <T extends AbstractContainerMenu> MenuType<T> register(
            String name,
            MenuType.MenuSupplier<T> constructor
    ) {
        return Registry.register(BuiltInRegistries.MENU, name, new MenuType<>(constructor, FeatureFlagSet.of()));
    }

    public static void initialize() { }
}
