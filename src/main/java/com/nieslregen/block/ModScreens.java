package com.nieslregen.block;

import com.nieslregen.block.herbariumpress.HerbariumScreen;
import net.minecraft.client.gui.screens.MenuScreens;

public class ModScreens {

    public static void initialize() {
        MenuScreens.register(ModMenuType.HERBARIUM_PRESS_MENU_TYPE, HerbariumScreen::new);
    }
}
