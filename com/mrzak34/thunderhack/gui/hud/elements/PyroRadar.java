//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import java.util.concurrent.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.mixin.mixins.*;

public class PyroRadar extends Module
{
    public Setting<Float> scale;
    public Setting<Float> distance;
    public Setting<Boolean> unlockTilt;
    public Setting<Integer> tilt;
    public Setting<Boolean> items;
    public Setting<Boolean> players;
    public Setting<Boolean> hidefrustum;
    public Setting<Boolean> other;
    public Setting<Boolean> bosses;
    public Setting<Boolean> hostiles;
    public Setting<Boolean> friends;
    public final Setting<ColorSetting> color;
    public final Setting<ColorSetting> fcolor;
    private CopyOnWriteArrayList<Entity> entityList;
    
    public PyroRadar() {
        super("PyroRadar", "\u0440\u0430\u0434\u0430\u0440 \u0438\u0437 \u043f\u0430\u0439\u0440\u043e", Category.HUD);
        this.scale = (Setting<Float>)this.register(new Setting("Scale", (T)0.2f, (T)0.0f, (T)1));
        this.distance = (Setting<Float>)this.register(new Setting("distance", (T)0.2f, (T)0.0f, (T)2.0f));
        this.unlockTilt = (Setting<Boolean>)this.register(new Setting("Unlock Tilt", (T)false));
        this.tilt = (Setting<Integer>)this.register(new Setting("Tilt", (T)50, (T)(-90), (T)90));
        this.items = (Setting<Boolean>)this.register(new Setting("items", (T)false));
        this.players = (Setting<Boolean>)this.register(new Setting("players", (T)false));
        this.hidefrustum = (Setting<Boolean>)this.register(new Setting("HideInFrustrum", (T)false));
        this.other = (Setting<Boolean>)this.register(new Setting("other", (T)false));
        this.bosses = (Setting<Boolean>)this.register(new Setting("bosses", (T)false));
        this.hostiles = (Setting<Boolean>)this.register(new Setting("hostiles", (T)false));
        this.friends = (Setting<Boolean>)this.register(new Setting("friends", (T)false));
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.fcolor = (Setting<ColorSetting>)this.register(new Setting("FriendColor", (T)new ColorSetting(-2013200640)));
        this.entityList = new CopyOnWriteArrayList<Entity>();
    }
    
    @Override
    public void onUpdate() {
        this.entityList.clear();
        this.entityList.addAll(PyroRadar.mc.world.loadedEntityList);
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent event) {
        for (final Entity ent : this.entityList) {
            if (ent == PyroRadar.mc.player) {
                continue;
            }
            if (this.hidefrustum.getValue() && RenderUtil.camera.isBoundingBoxInFrustum(ent.getEntityBoundingBox())) {
                continue;
            }
            if (ent instanceof EntityPlayer) {
                if (this.friends.getValue() && Thunderhack.friendManager.isFriend((EntityPlayer)ent)) {
                    this.drawArrow(ent, this.fcolor.getValue().getColorObject());
                }
                else {
                    if (!this.players.getValue() && Thunderhack.friendManager.isFriend((EntityPlayer)ent)) {
                        continue;
                    }
                    this.drawArrow(ent, this.color.getValue().getColorObject());
                }
            }
            else if (ent instanceof EntityWither) {
                if (!this.bosses.getValue()) {
                    continue;
                }
                this.drawArrow(ent, this.color.getValue().getColorObject());
            }
            else if (ent instanceof EntityDragon) {
                if (!this.bosses.getValue()) {
                    continue;
                }
                this.drawArrow(ent, this.color.getValue().getColorObject());
            }
            else if (ent.isCreatureType(EnumCreatureType.MONSTER, false)) {
                if (!this.hostiles.getValue()) {
                    continue;
                }
                this.drawArrow(ent, this.color.getValue().getColorObject());
            }
            else if (ent instanceof EntityItem) {
                if (!this.items.getValue()) {
                    continue;
                }
                this.drawArrow(ent, this.color.getValue().getColorObject());
            }
            else {
                if (!this.other.getValue()) {
                    continue;
                }
                this.drawArrow(ent, this.color.getValue().getColorObject());
            }
        }
    }
    
    private float getYaw(final Entity entity) {
        final double delta_x = RenderUtil.interpolate(entity.posX, entity.lastTickPosX) - RenderUtil.interpolate(PyroRadar.mc.player.posX, PyroRadar.mc.player.lastTickPosX);
        final double delta_z = RenderUtil.interpolate(entity.posZ, entity.lastTickPosZ) - RenderUtil.interpolate(PyroRadar.mc.player.posZ, PyroRadar.mc.player.lastTickPosZ);
        return MathHelper.wrapDegrees((float)Math.toDegrees(MathHelper.atan2(delta_x, delta_z)) + 180.0f);
    }
    
    public void arrow(final float f, final float f2, final float f3, final float f4) {
        GlStateManager.glBegin(6);
        GlStateManager.glVertex3f(f, f2, f3);
        GlStateManager.glVertex3f(f + 0.1f * f4, f2, f3 - 0.2f * f4);
        GlStateManager.glVertex3f(f, f2, f3 - 0.12f * f4);
        GlStateManager.glVertex3f(f - 0.1f * f4, f2, f3 - 0.2f * f4);
        GlStateManager.glEnd();
    }
    
    public void drawArrow(final Entity ent, final Color color) {
        if (PyroRadar.mc.entityRenderer != null) {
            final Vec3d var14 = new Vec3d(0.0, 0.0, 1.0).rotateYaw((float)Math.toRadians(this.getYaw(ent) + PyroRadar.mc.player.rotationYaw)).rotatePitch(3.1415927f);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), (float)color.getAlpha());
            GlStateManager.disableLighting();
            GlStateManager.loadIdentity();
            ((IEntityRenderer)PyroRadar.mc.entityRenderer).orientCam(PyroRadar.mc.getRenderPartialTicks());
            GlStateManager.translate(0.0f, PyroRadar.mc.player.getEyeHeight(), 0.0f);
            GlStateManager.rotate(-PyroRadar.mc.player.rotationYaw, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(PyroRadar.mc.player.rotationPitch, 1.0f, 0.0f, 0.0f);
            GlStateManager.translate(0.0f, 0.0f, 1.0f);
            float tilt_value = this.tilt.getValue();
            if (this.unlockTilt.getValue() && 90.0f - PyroRadar.mc.player.rotationPitch < tilt_value) {
                tilt_value = 90.0f - PyroRadar.mc.player.rotationPitch;
            }
            GlStateManager.rotate(tilt_value, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.translate(0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(this.getYaw(ent) + PyroRadar.mc.player.rotationYaw, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, 0.0f, this.distance.getValue() * 0.2f);
            this.arrow((float)var14.x, (float)var14.y, (float)var14.z, this.scale.getValue());
            GlStateManager.enableTexture2D();
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableLighting();
        }
    }
}
