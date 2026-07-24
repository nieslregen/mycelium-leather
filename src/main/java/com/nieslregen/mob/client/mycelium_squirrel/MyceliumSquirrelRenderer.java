package com.nieslregen.mob.client.mycelium_squirrel;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.mob.client.ModEntityModelLayers;
import com.nieslregen.mob.myceliumsquirrel.MyceliumSquirrel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class MyceliumSquirrelRenderer extends MobRenderer<MyceliumSquirrel, MyceliumSquirrelRenderState, MyceliumSquirrelModel> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "textures/entity/mycelium_squirrel.png");

    public MyceliumSquirrelRenderer(EntityRendererProvider.Context context) {
        super(context, new MyceliumSquirrelModel(context.bakeLayer(ModEntityModelLayers.MYCELIUM_SQUIRREL_LAYER)), .175F);
    }

    @Override
    public Identifier getTextureLocation(MyceliumSquirrelRenderState state) {
        return TEXTURE;
    }

    @Override
    public MyceliumSquirrelRenderState createRenderState() {
        return new MyceliumSquirrelRenderState();
    }

    @Override
    public void extractRenderState(MyceliumSquirrel entity, MyceliumSquirrelRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
    }
}
