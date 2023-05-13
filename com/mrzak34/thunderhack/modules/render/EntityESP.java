//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import javax.vecmath.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.math.*;
import java.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.util.glu.*;
import org.lwjgl.opengl.*;
import java.nio.*;

public class EntityESP extends Module
{
    public final Setting<Boolean> border;
    public final Setting<Boolean> fullBox;
    public final Setting<Boolean> heathPercentage;
    public final Setting<Boolean> healRect;
    public final Setting<Boolean> ignoreInvisible;
    private final int black;
    private final Setting<ColorSetting> colorEsp;
    private final Setting<ColorSetting> healColor;
    private final Setting<healcolorModeEn> healcolorMode;
    private final Setting<colorModeEn> colorMode;
    private final Setting<espModeEn> espMode;
    private final Setting<rectModeEn> rectMode;
    private final Setting<csgoModeEn> csgoMode;
    
    public EntityESP() {
        super("EntityESP", "\u0420\u0435\u043d\u0434\u043d\u0440\u0438\u0442 \u0435\u0441\u043f-\u0441\u0443\u0449\u043d\u043e\u0441\u0442\u0435\u0439", Module.Category.RENDER);
        this.border = (Setting<Boolean>)this.register(new Setting("Border Rect", (T)true));
        this.fullBox = (Setting<Boolean>)this.register(new Setting("Full Box", (T)false));
        this.heathPercentage = (Setting<Boolean>)this.register(new Setting("HealthPercent", (T)true));
        this.healRect = (Setting<Boolean>)this.register(new Setting("HealthRect", (T)true));
        this.ignoreInvisible = (Setting<Boolean>)this.register(new Setting("IgnoreInvisible", (T)true));
        this.black = Color.BLACK.getRGB();
        this.colorEsp = (Setting<ColorSetting>)this.register(new Setting("ESPColor", (T)new ColorSetting(575714484)));
        this.healColor = (Setting<ColorSetting>)this.register(new Setting("HealthColor", (T)new ColorSetting(575714484)));
        this.healcolorMode = (Setting<healcolorModeEn>)this.register(new Setting("HealthMode", (T)healcolorModeEn.Custom));
        this.colorMode = (Setting<colorModeEn>)this.register(new Setting("ColorBoxMode", (T)colorModeEn.Custom));
        this.espMode = (Setting<espModeEn>)this.register(new Setting("espMode", (T)espModeEn.Flat));
        this.rectMode = (Setting<rectModeEn>)this.register(new Setting("RectMode", (T)rectModeEn.Default));
        this.csgoMode = (Setting<csgoModeEn>)this.register(new Setting("csgoMode", (T)csgoModeEn.Box));
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event3D) {
        int color = 0;
        switch (this.colorMode.getValue()) {
            case Custom: {
                color = this.colorEsp.getValue().getColor();
                break;
            }
            case Astolfo: {
                color = DrawHelper.astolfo(false, 10).getRGB();
                break;
            }
            case Rainbow: {
                color = DrawHelper.rainbow(300, 1.0f, 1.0f).getRGB();
                break;
            }
        }
        if (this.espMode.getValue() == espModeEn.Box) {
            GlStateManager.pushMatrix();
            for (final Entity entity : EntityESP.mc.world.loadedEntityList) {
                if (entity instanceof EntityPlayer && entity != EntityESP.mc.player) {
                    DrawHelper.drawEntityBox(entity, new Color(color), this.fullBox.getValue(), ((boolean)this.fullBox.getValue()) ? 0.15f : 0.9f);
                }
            }
            GlStateManager.popMatrix();
        }
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent event) {
        final ScaledResolution sr = new ScaledResolution(EntityESP.mc);
        final int scaleFactor = sr.getScaleFactor();
        final double scaling = scaleFactor / Math.pow(scaleFactor, 2.0);
        GL11.glPushMatrix();
        GlStateManager.scale(scaling, scaling, scaling);
        int color = 0;
        switch (this.colorMode.getValue()) {
            case Custom: {
                color = this.colorEsp.getValue().getColor();
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
        for (final Entity entity : EntityESP.mc.world.loadedEntityList) {
            if (entity instanceof EntityPlayer && entity != EntityESP.mc.player) {
                if (this.ignoreInvisible.getValue() && entity.isInvisible()) {
                    continue;
                }
                if (!this.isValid(entity) || !DrawHelper.isInViewFrustum(entity)) {
                    continue;
                }
                final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * EntityESP.mc.getRenderPartialTicks();
                final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * EntityESP.mc.getRenderPartialTicks();
                final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * EntityESP.mc.getRenderPartialTicks();
                final double width = entity.width / 1.5;
                final double height = entity.height + ((entity.isSneaking() || (entity == EntityESP.mc.player && EntityESP.mc.player.isSneaking())) ? -0.3 : 0.2);
                final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
                final List<Vector3d> vectors = Arrays.asList(new Vector3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ), new Vector3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ));
                ((IEntityRenderer)EntityESP.mc.entityRenderer).invokeSetupCameraTransform(event.getPartialTicks(), 0);
                Vector4d position = null;
                for (Vector3d vector : vectors) {
                    vector = this.vectorRender2D(scaleFactor, vector.x - ((IRenderManager)EntityESP.mc.getRenderManager()).getRenderPosX(), vector.y - ((IRenderManager)EntityESP.mc.getRenderManager()).getRenderPosY(), vector.z - ((IRenderManager)EntityESP.mc.getRenderManager()).getRenderPosZ());
                    if (vector != null && vector.z > 0.0 && vector.z < 1.0) {
                        if (position == null) {
                            position = new Vector4d(vector.x, vector.y, vector.z, 0.0);
                        }
                        position.x = Math.min(vector.x, position.x);
                        position.y = Math.min(vector.y, position.y);
                        position.z = Math.max(vector.x, position.z);
                        position.w = Math.max(vector.y, position.w);
                    }
                }
                if (position == null) {
                    continue;
                }
                EntityESP.mc.entityRenderer.setupOverlayRendering();
                final double posX = position.x;
                final double posY = position.y;
                final double endPosX = position.z;
                final double endPosY = position.w;
                if (this.border.getValue()) {
                    if (this.espMode.getValue() == espModeEn.Flat && this.csgoMode.getValue() == csgoModeEn.Box && this.rectMode.getValue() == rectModeEn.Smooth) {
                        DrawHelper.drawSmoothRect(posX - 0.5, posY - 0.5, endPosX + 0.5, posY + 0.5 + 1.0, this.black);
                        DrawHelper.drawSmoothRect(posX - 0.5, endPosY - 0.5 - 1.0, endPosX + 0.5, endPosY + 0.5, this.black);
                        DrawHelper.drawSmoothRect(posX - 1.5, posY, posX + 0.5, endPosY + 0.5, this.black);
                        DrawHelper.drawSmoothRect(endPosX - 0.5 - 1.0, posY, endPosX + 0.5, endPosY + 0.5, this.black);
                        DrawHelper.drawSmoothRect(posX - 1.0, posY, posX + 0.5 - 0.5, endPosY, color);
                        DrawHelper.drawSmoothRect(posX, endPosY - 1.0, endPosX, endPosY, color);
                        DrawHelper.drawSmoothRect(posX - 1.0, posY, endPosX, posY + 1.0, color);
                        DrawHelper.drawSmoothRect(endPosX - 1.0, posY, endPosX, endPosY, color);
                    }
                    else if (this.espMode.getValue() == espModeEn.Flat && this.csgoMode.getValue() == csgoModeEn.Corner && this.rectMode.getValue() == rectModeEn.Smooth) {
                        DrawHelper.drawSmoothRect(posX + 1.0, posY, posX - 1.0, posY + (endPosY - posY) / 4.0 + 0.5, this.black);
                        DrawHelper.drawSmoothRect(posX - 1.0, endPosY, posX + 1.0, endPosY - (endPosY - posY) / 4.0 - 0.5, this.black);
                        DrawHelper.drawSmoothRect(posX - 1.0, posY - 0.5, posX + (endPosX - posX) / 3.0, posY + 1.0, this.black);
                        DrawHelper.drawSmoothRect(endPosX - (endPosX - posX) / 3.0 - 0.0, posY - 0.5, endPosX, posY + 1.5, this.black);
                        DrawHelper.drawSmoothRect(endPosX - 1.5, posY, endPosX + 0.5, posY + (endPosY - posY) / 4.0 + 0.5, this.black);
                        DrawHelper.drawSmoothRect(endPosX - 1.5, endPosY, endPosX + 0.5, endPosY - (endPosY - posY) / 4.0 - 0.5, this.black);
                        DrawHelper.drawSmoothRect(posX - 1.0, endPosY - 1.5, posX + (endPosX - posX) / 3.0 + 0.5, endPosY + 0.5, this.black);
                        DrawHelper.drawSmoothRect(endPosX - (endPosX - posX) / 3.0 - 0.5, endPosY - 1.5, endPosX + 0.5, endPosY + 0.5, this.black);
                        DrawHelper.drawSmoothRect(posX + 0.5, posY, posX - 0.5, posY + (endPosY - posY) / 4.0, color);
                        DrawHelper.drawSmoothRect(posX + 0.5, endPosY, posX - 0.5, endPosY - (endPosY - posY) / 4.0, color);
                        DrawHelper.drawSmoothRect(posX - 0.5, posY, posX + (endPosX - posX) / 3.0, posY + 1.0, color);
                        DrawHelper.drawSmoothRect(endPosX - (endPosX - posX) / 3.0 + 0.5, posY, endPosX, posY + 1.0, color);
                        DrawHelper.drawSmoothRect(endPosX - 1.0, posY, endPosX, posY + (endPosY - posY) / 4.0 + 0.5, color);
                        DrawHelper.drawSmoothRect(endPosX - 1.0, endPosY, endPosX, endPosY - (endPosY - posY) / 4.0, color);
                        DrawHelper.drawSmoothRect(posX, endPosY - 1.0, posX + (endPosX - posX) / 3.0, endPosY, color);
                        DrawHelper.drawSmoothRect(endPosX - (endPosX - posX) / 3.0, endPosY - 1.0, endPosX - 0.5, endPosY, color);
                    }
                    else if (this.espMode.getValue() == espModeEn.Flat && this.csgoMode.getValue() == csgoModeEn.Box && this.rectMode.getValue() == rectModeEn.Default) {
                        DrawHelper.drawNewRect(posX - 0.5, posY - 0.5, endPosX + 0.5, posY + 0.5 + 1.0, this.black);
                        DrawHelper.drawNewRect(posX - 0.5, endPosY - 0.5 - 1.0, endPosX + 0.5, endPosY + 0.5, this.black);
                        DrawHelper.drawNewRect(posX - 1.5, posY, posX + 0.5, endPosY + 0.5, this.black);
                        DrawHelper.drawNewRect(endPosX - 0.5 - 1.0, posY, endPosX + 0.5, endPosY + 0.5, this.black);
                        DrawHelper.drawNewRect(posX - 1.0, posY, posX + 0.5 - 0.5, endPosY, color);
                        DrawHelper.drawNewRect(posX, endPosY - 1.0, endPosX, endPosY, color);
                        DrawHelper.drawNewRect(posX - 1.0, posY, endPosX, posY + 1.0, color);
                        DrawHelper.drawNewRect(endPosX - 1.0, posY, endPosX, endPosY, color);
                    }
                    else if (this.espMode.getValue() == espModeEn.Flat && this.csgoMode.getValue() == csgoModeEn.Corner && this.rectMode.getValue() == rectModeEn.Default) {
                        DrawHelper.drawNewRect(posX + 1.0, posY, posX - 1.0, posY + (endPosY - posY) / 4.0 + 0.5, this.black);
                        DrawHelper.drawNewRect(posX - 1.0, endPosY, posX + 1.0, endPosY - (endPosY - posY) / 4.0 - 0.5, this.black);
                        DrawHelper.drawNewRect(posX - 1.0, posY - 0.5, posX + (endPosX - posX) / 3.0, posY + 1.0, this.black);
                        DrawHelper.drawNewRect(endPosX - (endPosX - posX) / 3.0 - 0.0, posY - 0.5, endPosX, posY + 1.5, this.black);
                        DrawHelper.drawNewRect(endPosX - 1.5, posY, endPosX + 0.5, posY + (endPosY - posY) / 4.0 + 0.5, this.black);
                        DrawHelper.drawNewRect(endPosX - 1.5, endPosY, endPosX + 0.5, endPosY - (endPosY - posY) / 4.0 - 0.5, this.black);
                        DrawHelper.drawNewRect(posX - 1.0, endPosY - 1.5, posX + (endPosX - posX) / 3.0 + 0.5, endPosY + 0.5, this.black);
                        DrawHelper.drawNewRect(endPosX - (endPosX - posX) / 3.0 - 0.5, endPosY - 1.5, endPosX + 0.5, endPosY + 0.5, this.black);
                        DrawHelper.drawNewRect(posX + 0.5, posY, posX - 0.5, posY + (endPosY - posY) / 4.0, color);
                        DrawHelper.drawNewRect(posX + 0.5, endPosY, posX - 0.5, endPosY - (endPosY - posY) / 4.0, color);
                        DrawHelper.drawNewRect(posX - 0.5, posY, posX + (endPosX - posX) / 3.0, posY + 1.0, color);
                        DrawHelper.drawNewRect(endPosX - (endPosX - posX) / 3.0 + 0.5, posY, endPosX, posY + 1.0, color);
                        DrawHelper.drawNewRect(endPosX - 1.0, posY, endPosX, posY + (endPosY - posY) / 4.0 + 0.5, color);
                        DrawHelper.drawNewRect(endPosX - 1.0, endPosY, endPosX, endPosY - (endPosY - posY) / 4.0, color);
                        DrawHelper.drawNewRect(posX, endPosY - 1.0, posX + (endPosX - posX) / 3.0, endPosY, color);
                        DrawHelper.drawNewRect(endPosX - (endPosX - posX) / 3.0, endPosY - 1.0, endPosX - 0.5, endPosY, color);
                    }
                }
                final boolean living = entity instanceof EntityLivingBase;
                final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
                float targetHP = entityLivingBase.getHealth();
                targetHP = MathHelper.clamp(targetHP, 0.0f, 24.0f);
                final float maxHealth = entityLivingBase.getMaxHealth();
                final double hpPercentage = targetHP / maxHealth;
                final double hpHeight2 = (endPosY - posY) * hpPercentage;
                if (!living || !this.healRect.getValue() || this.espMode.getValue() == espModeEn.Box) {
                    continue;
                }
                int colorHeal = 0;
                switch (this.healcolorMode.getValue()) {
                    case Custom: {
                        colorHeal = this.healColor.getValue().getColor();
                        break;
                    }
                    case Astolfo: {
                        colorHeal = DrawHelper.astolfo(false, (int)entity.height).getRGB();
                        break;
                    }
                    case Rainbow: {
                        colorHeal = DrawHelper.rainbow(300, 1.0f, 1.0f).getRGB();
                        break;
                    }
                    case Health: {
                        colorHeal = DrawHelper.getHealthColor(((EntityLivingBase)entity).getHealth(), ((EntityLivingBase)entity).getMaxHealth());
                        break;
                    }
                }
                if (targetHP <= 0.0f) {
                    continue;
                }
                final String string2 = "" + MathematicHelper.round(entityLivingBase.getHealth() / entityLivingBase.getMaxHealth() * 20.0f, 1);
                if (living && this.heathPercentage.getValue() && this.espMode.getValue() != espModeEn.Box && this.heathPercentage.getValue()) {
                    GlStateManager.pushMatrix();
                    EntityESP.mc.fontRenderer.drawStringWithShadow(string2, (float)posX - 30.0f, (float)((float)endPosY - hpHeight2), -1);
                    GlStateManager.popMatrix();
                }
                DrawHelper.drawRect(posX - 5.0, posY - 0.5, posX - 2.5, endPosY + 0.5, new Color(0, 0, 0, 125).getRGB());
                DrawHelper.drawRect(posX - 4.5, endPosY, posX - 3.0, endPosY - hpHeight2, colorHeal);
            }
        }
        GL11.glEnable(2929);
        GlStateManager.enableBlend();
        GL11.glPopMatrix();
        EntityESP.mc.entityRenderer.setupOverlayRendering();
    }
    
    private boolean isValid(final Entity entity) {
        return (EntityESP.mc.gameSettings.thirdPersonView != 0 || entity != EntityESP.mc.player) && !entity.isDead && !(entity instanceof EntityAnimal) && (entity instanceof EntityPlayer || (!(entity instanceof EntityArmorStand) && !(entity instanceof IAnimals) && !(entity instanceof EntityItemFrame) && !(entity instanceof EntityArrow) && !(entity instanceof EntityMinecart) && !(entity instanceof EntityBoat) && !(entity instanceof EntityDragonFireball) && !(entity instanceof EntityXPOrb) && !(entity instanceof EntityTNTPrimed) && !(entity instanceof EntityExpBottle) && !(entity instanceof EntityLightningBolt) && !(entity instanceof EntityPotion) && !(entity instanceof Entity) && !(entity instanceof EntityMob) && !(entity instanceof EntitySlime) && !(entity instanceof EntityDragon) && !(entity instanceof EntityGolem) && entity != EntityESP.mc.player));
    }
    
    private Vector3d vectorRender2D(final int scaleFactor, final double x, final double y, final double z) {
        final float xPos = (float)x;
        final float yPos = (float)y;
        final float zPos = (float)z;
        final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
        final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
        final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
        final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
        GL11.glGetFloat(2982, modelview);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);
        if (GLU.gluProject(xPos, yPos, zPos, modelview, projection, viewport, vector)) {
            return new Vector3d((double)(vector.get(0) / scaleFactor), (double)((Display.getHeight() - vector.get(1)) / scaleFactor), (double)vector.get(2));
        }
        return null;
    }
    
    public enum healcolorModeEn
    {
        Custom, 
        Astolfo, 
        Health, 
        Rainbow, 
        Client;
    }
    
    public enum colorModeEn
    {
        Custom, 
        Astolfo, 
        Rainbow, 
        Client;
    }
    
    public enum espModeEn
    {
        Flat, 
        Box;
    }
    
    public enum rectModeEn
    {
        Default, 
        Smooth;
    }
    
    public enum csgoModeEn
    {
        Box, 
        Corner;
    }
}
