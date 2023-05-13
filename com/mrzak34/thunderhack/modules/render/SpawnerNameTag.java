//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.tileentity.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.util.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.util.render.*;
import org.apache.commons.lang3.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class SpawnerNameTag extends Module
{
    public final Setting<ColorSetting> rectcolor;
    public final Setting<ColorSetting> color;
    private final Setting<Float> scaling;
    private final Setting<Boolean> scaleing;
    private final Setting<Float> factor;
    
    public SpawnerNameTag() {
        super("SpawnerNameTag", "\u041f\u043e\u0434\u0441\u0432\u0435\u0447\u0438\u0432\u0430\u0435\u0442 \u0441\u043f\u0430\u0432\u043d\u0435\u0440\u0430", "spawner esp", Module.Category.RENDER);
        this.rectcolor = (Setting<ColorSetting>)this.register(new Setting("RectColor", (T)new ColorSetting(1426063360)));
        this.color = (Setting<ColorSetting>)this.register(new Setting("ESPColor", (T)new ColorSetting(-2013200640)));
        this.scaling = (Setting<Float>)this.register(new Setting("Size", (T)20.0f, (T)0.1f, (T)30.0f));
        this.scaleing = (Setting<Boolean>)this.register(new Setting("Scale", (T)true));
        this.factor = (Setting<Float>)this.register(new Setting("Factor", (T)0.17f, (T)0.1f, (T)1.0f));
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        for (final TileEntity tileent : SpawnerNameTag.mc.world.loadedTileEntityList) {
            if (tileent instanceof TileEntityMobSpawner) {
                final TileEntityMobSpawner spawner = (TileEntityMobSpawner)tileent;
                final double n = spawner.getPos().getX();
                SpawnerNameTag.mc.getRenderManager();
                final double x = n - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosX();
                final double n2 = spawner.getPos().getY();
                SpawnerNameTag.mc.getRenderManager();
                final double y = n2 - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosY();
                final double n3 = spawner.getPos().getZ();
                SpawnerNameTag.mc.getRenderManager();
                final double z = n3 - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosZ();
                GL11.glPushMatrix();
                RenderUtil.drawBlockOutline(spawner.getPos(), this.color.getValue().getColorObject(), 3.0f, true, 0);
                RenderHelper.disableStandardItemLighting();
                final String entity = StringUtils.substringBetween(spawner.getUpdateTag().toString(), "id:\"minecraft:", "\"");
                final int time = Integer.parseInt(StringUtils.substringBetween(spawner.getUpdateTag().toString(), ",Delay:", "s,")) / 20;
                this.renderNameTag(x + 0.5, y, z + 0.5, event.getPartialTicks(), entity + " " + time + " s");
                RenderHelper.enableStandardItemLighting();
                GL11.glPopMatrix();
            }
        }
    }
    
    private void renderNameTag(final double x, final double y, final double z, final float delta, final String displayTag) {
        double tempY = y;
        tempY += 0.7;
        final Entity camera = NameTags.mc.getRenderViewEntity();
        assert camera != null;
        final double originalPositionX = camera.posX;
        final double originalPositionY = camera.posY;
        final double originalPositionZ = camera.posZ;
        camera.posX = RenderUtil.interpolate(camera.prevPosX, camera.posX, delta);
        camera.posY = RenderUtil.interpolate(camera.prevPosY, camera.posY, delta);
        camera.posZ = RenderUtil.interpolate(camera.prevPosZ, camera.posZ, delta);
        final double distance = camera.getDistance(x + NameTags.mc.getRenderManager().viewerPosX, y + NameTags.mc.getRenderManager().viewerPosY, z + NameTags.mc.getRenderManager().viewerPosZ);
        final int width = SpawnerNameTag.mc.fontRenderer.getStringWidth(displayTag) / 2;
        double scale = (0.0018 + this.scaling.getValue() * (distance * this.factor.getValue())) / 1000.0;
        if (!this.scaleing.getValue()) {
            scale = this.scaling.getValue() / 100.0;
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float)x, (float)tempY + 1.4f, (float)z);
        GlStateManager.rotate(-NameTags.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(NameTags.mc.getRenderManager().playerViewX, (NameTags.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        RenderUtil.drawRect((float)(-width - 2), -4.0f, width + 2.0f, 4.0f, this.rectcolor.getValue().getColor());
        GlStateManager.disableBlend();
        SpawnerNameTag.mc.fontRenderer.drawStringWithShadow(displayTag, (float)(-width), -4.0f, -1);
        camera.posX = originalPositionX;
        camera.posY = originalPositionY;
        camera.posZ = originalPositionZ;
        GlStateManager.enableDepth();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
    }
}
