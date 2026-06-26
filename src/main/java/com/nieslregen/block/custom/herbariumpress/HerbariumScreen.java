package com.nieslregen.block.custom.herbariumpress;

import com.nieslregen.MyceliumLeatherMod;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Environment(EnvType.CLIENT)
public class HerbariumScreen extends AbstractContainerScreen<HerbariumPressMenu> {

    private final static int width = 176;
    private final static int height = 166;

    private static final Identifier CONTAINER_TEXTURE =
            Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "textures/gui/container/herbarium_press.png");

    private static final Identifier PROGRESS_BAR_TEXTURE =
            Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "textures/gui/container/herbarium_press_progress_bar.png");

    private static final Logger log = LoggerFactory.getLogger(HerbariumScreen.class);

    public HerbariumScreen(HerbariumPressMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);

    }

    public int getProgress() {
        return this.menu.getPressingProgress();
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractBackground(graphics, mouseX, mouseY, delta);
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                CONTAINER_TEXTURE,
                this.leftPos,
                this.topPos,
                0.0F,
                0.0F,
                width,
                height,
                width,
                height
        );

        float relative = this.getProgress() / (float) HerbariumPressEntity.maxProgress;
        int progressHeight = Mth.ceil(relative * 14F);

        int X = this.leftPos + 81;
        int Y = this.topPos + 35;

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                PROGRESS_BAR_TEXTURE,
                X,
                Y,
                0,
                0,
                13,
                progressHeight,
                13,
                14
        );
    }
}