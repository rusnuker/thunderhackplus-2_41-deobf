//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class CrystalChams extends Module
{
    public final Setting<ColorSetting> color;
    public final Setting<ColorSetting> wireFrameColor;
    public Setting<Float> lineWidth;
    public Setting<Boolean> chams;
    public Setting<Boolean> throughWalls;
    public Setting<Boolean> wireframe;
    public Setting<Boolean> wireWalls;
    public Setting<Boolean> texture;
    private final Setting<ChamsMode> mode;
    
    public CrystalChams() {
        super("CrystalChams", "CrystalChams", Module.Category.MISC);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(3649978)));
        this.wireFrameColor = (Setting<ColorSetting>)this.register(new Setting("WireframeColor", (T)new ColorSetting(3649978)));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)0.1f, (T)6.0f));
        this.chams = (Setting<Boolean>)this.register(new Setting("Chams", (T)true));
        this.throughWalls = (Setting<Boolean>)this.register(new Setting("ThroughWalls", (T)true));
        this.wireframe = (Setting<Boolean>)this.register(new Setting("Wireframe", (T)true));
        this.wireWalls = (Setting<Boolean>)this.register(new Setting("WireThroughWalls", (T)true));
        this.texture = (Setting<Boolean>)this.register(new Setting("Texture", (T)false));
        this.mode = (Setting<ChamsMode>)this.register(new Setting("Mode", (T)ChamsMode.Normal));
    }
    
    @SubscribeEvent
    public void onRenderCrystal(final CrystalRenderEvent.Pre e) {
        if (!this.texture.getValue()) {
            e.setCanceled(true);
        }
        if (this.mode.getValue() == ChamsMode.Gradient) {
            GL11.glPushAttrib(1048575);
            GL11.glEnable(3042);
            GL11.glDisable(2896);
            GL11.glDisable(3553);
            final float alpha = this.color.getValue().getAlpha() / 255.0f;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
            e.getModel().render(e.getEntity(), e.getLimbSwing(), e.getLimbSwingAmount(), e.getAgeInTicks(), e.getNetHeadYaw(), e.getHeadPitch(), e.getScale());
            GL11.glEnable(3553);
            GL11.glBlendFunc(770, 771);
            final float f = e.getEntity().ticksExisted + CrystalChams.mc.getRenderPartialTicks();
            CrystalChams.mc.getTextureManager().bindTexture(new ResourceLocation("textures/rainbow.png"));
            CrystalChams.mc.entityRenderer.setupFogColor(true);
            GlStateManager.enableBlend();
            GlStateManager.depthFunc(514);
            GlStateManager.depthMask(false);
            GlStateManager.color(1.0f, 1.0f, 1.0f, alpha);
            for (int i = 0; i < 2; ++i) {
                GlStateManager.disableLighting();
                GlStateManager.color(1.0f, 1.0f, 1.0f, alpha);
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                GlStateManager.scale(0.33333334f, 0.33333334f, 0.33333334f);
                GlStateManager.rotate(30.0f - i * 60.0f, 0.0f, 0.0f, 0.5f);
                GlStateManager.translate(0.0f, f * (0.001f + i * 0.003f) * 20.0f, 0.0f);
                GlStateManager.matrixMode(5888);
                e.getModel().render(e.getEntity(), e.getLimbSwing(), e.getLimbSwingAmount(), e.getAgeInTicks(), e.getNetHeadYaw(), e.getHeadPitch(), e.getScale());
            }
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.depthMask(true);
            GlStateManager.depthFunc(515);
            GlStateManager.disableBlend();
            CrystalChams.mc.entityRenderer.setupFogColor(false);
            GL11.glPopAttrib();
        }
        else {
            if (this.wireframe.getValue()) {
                final Color wireColor = this.wireFrameColor.getValue().getColorObject();
                GL11.glPushAttrib(1048575);
                GL11.glEnable(3042);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glBlendFunc(770, 771);
                GL11.glPolygonMode(1032, 6913);
                GL11.glLineWidth((float)this.lineWidth.getValue());
                if (this.wireWalls.getValue()) {
                    GL11.glDepthMask(false);
                    GL11.glDisable(2929);
                }
                GL11.glColor4f(wireColor.getRed() / 255.0f, wireColor.getGreen() / 255.0f, wireColor.getBlue() / 255.0f, wireColor.getAlpha() / 255.0f);
                e.getModel().render(e.getEntity(), e.getLimbSwing(), e.getLimbSwingAmount(), e.getAgeInTicks(), e.getNetHeadYaw(), e.getHeadPitch(), e.getScale());
                GL11.glPopAttrib();
            }
            if (this.chams.getValue()) {
                final Color chamsColor = this.color.getValue().getColorObject();
                GL11.glPushAttrib(1048575);
                GL11.glEnable(3042);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glDisable(3008);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(2960);
                GL11.glEnable(10754);
                if (this.throughWalls.getValue()) {
                    GL11.glDepthMask(false);
                    GL11.glDisable(2929);
                }
                GL11.glColor4f(chamsColor.getRed() / 255.0f, chamsColor.getGreen() / 255.0f, chamsColor.getBlue() / 255.0f, chamsColor.getAlpha() / 255.0f);
                e.getModel().render(e.getEntity(), e.getLimbSwing(), e.getLimbSwingAmount(), e.getAgeInTicks(), e.getNetHeadYaw(), e.getHeadPitch(), e.getScale());
                GL11.glPopAttrib();
            }
        }
    }
    
    public enum ChamsMode
    {
        Normal, 
        Gradient;
    }
}
