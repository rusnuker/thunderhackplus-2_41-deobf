//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.gui.hud.*;
import com.mrzak34.thunderhack.util.math.*;
import com.mrzak34.thunderhack.setting.*;
import java.util.concurrent.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import com.mrzak34.thunderhack.gui.clickui.*;
import com.mrzak34.thunderhack.util.render.*;

public class RadarRewrite extends HudElement
{
    public static AstolfoAnimation astolfo;
    public Setting<Boolean> glow;
    float xOffset2;
    float yOffset2;
    private final Setting<Float> width;
    private final Setting<Float> rad22ius;
    private final Setting<Float> tracerA;
    private final Setting<Integer> xOffset;
    private final Setting<Integer> maxup2;
    private final Setting<Integer> glowe;
    private final Setting<Integer> glowa;
    private final Setting<triangleModeEn> triangleMode;
    private final Setting<mode2> Mode2;
    private final Setting<Float> CRadius;
    private final Setting<Integer> fsef;
    public final Setting<Integer> colorOffset1;
    public final Setting<ColorSetting> cColor2;
    private final Setting<ColorSetting> cColor;
    private final Setting<ColorSetting> ciColor;
    private final Setting<ColorSetting> colorf;
    private final Setting<ColorSetting> colors;
    private CopyOnWriteArrayList<EntityPlayer> players;
    
    public RadarRewrite() {
        super("AkrienRadar", "\u0441\u0442\u0440\u0435\u043b\u043e\u0447\u043a\u0438", 50, 50);
        this.glow = (Setting<Boolean>)this.register(new Setting("TracerGlow", (T)false));
        this.xOffset2 = 0.0f;
        this.yOffset2 = 0.0f;
        this.width = (Setting<Float>)this.register(new Setting("TracerHeight", (T)2.28f, (T)0.1f, (T)5.0f));
        this.rad22ius = (Setting<Float>)this.register(new Setting("TracerDown", (T)3.63f, (T)0.1f, (T)20.0f));
        this.tracerA = (Setting<Float>)this.register(new Setting("TracerWidth", (T)0.44f, (T)0.0f, (T)8.0f));
        this.xOffset = (Setting<Integer>)this.register(new Setting("TracerRadius", (T)68, (T)20, (T)100));
        this.maxup2 = (Setting<Integer>)this.register(new Setting("PitchLock", (T)42, (T)(-90), (T)90));
        this.glowe = (Setting<Integer>)this.register(new Setting("GlowRadius", (T)10, (T)1, (T)20));
        this.glowa = (Setting<Integer>)this.register(new Setting("GlowAlpha", (T)170, (T)0, (T)255));
        this.triangleMode = (Setting<triangleModeEn>)this.register(new Setting("TracerCMode", (T)triangleModeEn.Astolfo));
        this.Mode2 = (Setting<mode2>)this.register(new Setting("CircleCMode", (T)mode2.Astolfo));
        this.CRadius = (Setting<Float>)this.register(new Setting("CompasRadius", (T)47.0f, (T)0.1f, (T)70.0f));
        this.fsef = (Setting<Integer>)this.register(new Setting("Correct", (T)12, (T)(-90), (T)90));
        this.colorOffset1 = (Setting<Integer>)this.register(new Setting("ColorOffset", (T)10, (T)1, (T)20));
        this.cColor2 = (Setting<ColorSetting>)this.register(new Setting("CompassColor2", (T)new ColorSetting(575714484)));
        this.cColor = (Setting<ColorSetting>)this.register(new Setting("CompassColor", (T)new ColorSetting(575714484)));
        this.ciColor = (Setting<ColorSetting>)this.register(new Setting("CircleColor", (T)new ColorSetting(575714484)));
        this.colorf = (Setting<ColorSetting>)this.register(new Setting("FriendColor", (T)new ColorSetting(575714484)));
        this.colors = (Setting<ColorSetting>)this.register(new Setting("TracerColor", (T)new ColorSetting(575714484)));
        this.players = new CopyOnWriteArrayList<EntityPlayer>();
    }
    
    public static float clamp2(final float num, final float min, final float max) {
        if (num < min) {
            return min;
        }
        return Math.min(num, max);
    }
    
    public static void hexColor(final int hexColor) {
        final float red = (hexColor >> 16 & 0xFF) / 255.0f;
        final float green = (hexColor >> 8 & 0xFF) / 255.0f;
        final float blue = (hexColor & 0xFF) / 255.0f;
        final float alpha = (hexColor >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public static float getRotations(final Entity entity) {
        final double x = interp(entity.posX, entity.lastTickPosX) - interp(RadarRewrite.mc.player.posX, RadarRewrite.mc.player.lastTickPosX);
        final double z = interp(entity.posZ, entity.lastTickPosZ) - interp(RadarRewrite.mc.player.posZ, RadarRewrite.mc.player.lastTickPosZ);
        return (float)(-(Math.atan2(x, z) * 57.29577951308232));
    }
    
    public static double interp(final double d, final double d2) {
        return d2 + (d - d2) * RadarRewrite.mc.getRenderPartialTicks();
    }
    
    public static float getRotations(final BlockPos entity) {
        final double x = entity.getX() - interp(RadarRewrite.mc.player.posX, RadarRewrite.mc.player.lastTickPosX);
        final double z = entity.getZ() - interp(RadarRewrite.mc.player.posZ, RadarRewrite.mc.player.lastTickPosZ);
        return (float)(-(Math.atan2(x, z) * 57.29577951308232));
    }
    
    @Override
    public boolean isHovering() {
        return this.normaliseX() > this.xOffset2 - 50.0f && this.normaliseX() < this.xOffset2 + 50.0f && this.normaliseY() > this.yOffset2 - 50.0f && this.normaliseY() < this.yOffset2 + 50.0f;
    }
    
    @Override
    public void onUpdate() {
        this.players.clear();
        this.players.addAll(RadarRewrite.mc.world.playerEntities);
        RadarRewrite.astolfo.update();
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent event) {
        super.onRender2D(event);
        GlStateManager.pushMatrix();
        this.rendercompass();
        GlStateManager.popMatrix();
        this.xOffset2 = event.scaledResolution.getScaledWidth() * this.getX();
        this.yOffset2 = event.scaledResolution.getScaledHeight() * this.getY();
        int color = 0;
        switch (this.triangleMode.getValue()) {
            case Custom: {
                color = this.colors.getValue().getColor();
                break;
            }
            case Astolfo: {
                color = DrawHelper.astolfo(false, 1).getRGB();
                break;
            }
            case Rainbow: {
                color = DrawHelper.rainbow(300, 1.0f, 1.0f).getRGB();
                break;
            }
        }
        final float xOffset = event.scaledResolution.getScaledWidth() * this.getX();
        final float yOffset = event.scaledResolution.getScaledHeight() * this.getY();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.xOffset2, this.yOffset2, 0.0f);
        GL11.glRotatef(90.0f / Math.abs(90.0f / clamp2(RadarRewrite.mc.player.rotationPitch, this.maxup2.getValue(), 90.0f)) - 90.0f - this.fsef.getValue(), 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(-this.xOffset2, -this.yOffset2, 0.0f);
        for (final EntityPlayer e : this.players) {
            if (e != RadarRewrite.mc.player) {
                GL11.glPushMatrix();
                final float yaw = getRotations((Entity)e) - RadarRewrite.mc.player.rotationYaw;
                GL11.glTranslatef(xOffset, yOffset, 0.0f);
                GL11.glRotatef(yaw, 0.0f, 0.0f, 1.0f);
                GL11.glTranslatef(-xOffset, -yOffset, 0.0f);
                if (Thunderhack.friendManager.isFriend(e)) {
                    this.drawTracerPointer(xOffset, yOffset - this.xOffset.getValue(), this.width.getValue() * 5.0f, this.colorf.getValue().getColor());
                }
                else {
                    this.drawTracerPointer(xOffset, yOffset - this.xOffset.getValue(), this.width.getValue() * 5.0f, color);
                }
                GL11.glTranslatef(xOffset, yOffset, 0.0f);
                GL11.glRotatef(-yaw, 0.0f, 0.0f, 1.0f);
                GL11.glTranslatef(-xOffset, -yOffset, 0.0f);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glPopMatrix();
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }
    
    public void rendercompass() {
        final ScaledResolution sr = new ScaledResolution(RadarRewrite.mc);
        final float x = sr.getScaledWidth() * this.getX();
        final float y = sr.getScaledHeight() * this.getY();
        final float nigga = Math.abs(90.0f / clamp2(RadarRewrite.mc.player.rotationPitch, this.maxup2.getValue(), 90.0f));
        if (this.Mode2.getValue() == mode2.Custom) {
            RenderHelper.drawEllipsCompas(-(int)RadarRewrite.mc.player.rotationYaw, x, y, nigga, 1.0f, this.CRadius.getValue() - 2.0f, this.ciColor.getValue().getColorObject(), false, this.Mode2.getValue());
            RenderHelper.drawEllipsCompas(-(int)RadarRewrite.mc.player.rotationYaw, x, y, nigga, 1.0f, this.CRadius.getValue() - 2.5f, this.ciColor.getValue().getColorObject(), false, this.Mode2.getValue());
        }
        if (this.Mode2.getValue() == mode2.Rainbow) {
            RenderHelper.drawEllipsCompas(-(int)RadarRewrite.mc.player.rotationYaw, x, y, nigga, 1.0f, this.CRadius.getValue() - 2.0f, PaletteHelper.rainbow(300, 1.0f, 1.0f), false, this.Mode2.getValue());
            RenderHelper.drawEllipsCompas(-(int)RadarRewrite.mc.player.rotationYaw, x, y, nigga, 1.0f, this.CRadius.getValue() - 2.5f, PaletteHelper.rainbow(300, 1.0f, 1.0f), false, this.Mode2.getValue());
        }
        if (this.Mode2.getValue() == mode2.Astolfo) {
            RenderHelper.drawEllipsCompas(-(int)RadarRewrite.mc.player.rotationYaw, x, y, nigga, 1.0f, this.CRadius.getValue() - 2.0f, this.ciColor.getValue().getColorObject(), false, this.Mode2.getValue());
            RenderHelper.drawEllipsCompas(-(int)RadarRewrite.mc.player.rotationYaw, x, y, nigga, 1.0f, this.CRadius.getValue() - 2.5f, this.ciColor.getValue().getColorObject(), false, this.Mode2.getValue());
        }
        if (this.Mode2.getValue() == mode2.TwoColor) {
            RenderHelper.drawEllipsCompas(-(int)RadarRewrite.mc.player.rotationYaw, x, y, nigga, 1.0f, this.CRadius.getValue() - 2.0f, this.ciColor.getValue().getColorObject(), false, this.Mode2.getValue());
            RenderHelper.drawEllipsCompas(-(int)RadarRewrite.mc.player.rotationYaw, x, y, nigga, 1.0f, this.CRadius.getValue() - 2.5f, this.ciColor.getValue().getColorObject(), false, this.Mode2.getValue());
        }
        RenderHelper.drawEllipsCompas(-(int)RadarRewrite.mc.player.rotationYaw, x, y, nigga, 1.0f, this.CRadius.getValue(), this.cColor.getValue().getColorObject(), true, mode2.Custom);
    }
    
    public void drawTracerPointer(final float x, final float y, final float size, final int color) {
        final boolean blend = GL11.glIsEnabled(3042);
        GL11.glEnable(3042);
        final boolean depth = GL11.glIsEnabled(2929);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        hexColor(color);
        GL11.glBegin(7);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)(x - size * this.tracerA.getValue()), (double)(y + size));
        GL11.glVertex2d((double)x, (double)(y + size - this.rad22ius.getValue()));
        GL11.glVertex2d((double)x, (double)y);
        GL11.glEnd();
        hexColor(ColorUtil.darker(new Color(color), 0.8f).getRGB());
        GL11.glBegin(7);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)(y + size - this.rad22ius.getValue()));
        GL11.glVertex2d((double)(x + size * this.tracerA.getValue()), (double)(y + size));
        GL11.glVertex2d((double)x, (double)y);
        GL11.glEnd();
        hexColor(ColorUtil.darker(new Color(color), 0.6f).getRGB());
        GL11.glBegin(7);
        GL11.glVertex2d((double)(x - size * this.tracerA.getValue()), (double)(y + size));
        GL11.glVertex2d((double)(x + size * this.tracerA.getValue()), (double)(y + size));
        GL11.glVertex2d((double)x, (double)(y + size - this.rad22ius.getValue()));
        GL11.glVertex2d((double)(x - size * this.tracerA.getValue()), (double)(y + size));
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        if (!blend) {
            GL11.glDisable(3042);
        }
        GL11.glDisable(2848);
        if (this.glow.getValue()) {
            Drawable.drawBlurredShadow(x - size * this.tracerA.getValue(), y, x + size * this.tracerA.getValue() - (x - size * this.tracerA.getValue()), size, this.glowe.getValue(), DrawHelper.injectAlpha(new Color(color), this.glowa.getValue()));
        }
        if (depth) {
            GL11.glEnable(2929);
        }
    }
    
    static {
        RadarRewrite.astolfo = new AstolfoAnimation();
    }
    
    public enum mode2
    {
        Custom, 
        Rainbow, 
        Astolfo, 
        TwoColor;
    }
    
    public enum triangleModeEn
    {
        Custom, 
        Astolfo, 
        Rainbow;
    }
}
