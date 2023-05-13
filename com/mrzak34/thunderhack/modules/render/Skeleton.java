//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import java.util.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.client.model.*;
import com.mrzak34.thunderhack.events.*;
import org.lwjgl.opengl.*;
import java.util.function.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.util.*;

public class Skeleton extends Module
{
    private static final HashMap<EntityPlayer, float[][]> entities;
    public final Setting<ColorSetting> Color3;
    private final Setting<Float> lineWidth;
    private final Setting<Boolean> invisibles;
    
    public Skeleton() {
        super("Skeleton", "\u0441\u043a\u0435\u043b\u0435\u0442\u043e\u043d \u0435\u0441\u043f-\u043d\u0430 \u0438\u0433\u0440\u043e\u043a\u043e\u0432", Module.Category.RENDER);
        this.Color3 = (Setting<ColorSetting>)this.register(new Setting("FriendColor", (T)new ColorSetting(-2013200640)));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)0.1f, (T)5.0f));
        this.invisibles = (Setting<Boolean>)this.register(new Setting("Invisibles", (T)false));
    }
    
    public static void addEntity(final EntityPlayer e, final ModelPlayer model) {
        Skeleton.entities.put(e, new float[][] { { model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ }, { model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ }, { model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ }, { model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ }, { model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ } });
    }
    
    private Vec3d getVec3(final Render3DEvent event, final EntityPlayer e) {
        final float pt = event.getPartialTicks();
        final double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * pt;
        final double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * pt;
        final double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * pt;
        return new Vec3d(x, y, z);
    }
    
    public void onRender3D(final Render3DEvent event) {
        if (fullNullCheck()) {
            return;
        }
        this.startEnd(true);
        GL11.glEnable(2903);
        GL11.glDisable(2848);
        Skeleton.entities.keySet().removeIf(this::doesntContain);
        Skeleton.mc.world.playerEntities.forEach(e -> this.drawSkeleton(event, e));
        Gui.drawRect(0, 0, 0, 0, 0);
        this.startEnd(false);
    }
    
    private void drawSkeleton(final Render3DEvent event, final EntityPlayer e) {
        if (!BlockUtils.isPosInFov(new BlockPos(e.posX, e.posY, e.posZ))) {
            return;
        }
        if (e.isInvisible() && !this.invisibles.getValue()) {
            return;
        }
        final float[][] entPos = Skeleton.entities.get(e);
        if (entPos != null && e.isEntityAlive() && !e.isDead && e != Skeleton.mc.player && !e.isPlayerSleeping()) {
            GL11.glPushMatrix();
            GL11.glEnable(2848);
            GL11.glLineWidth((float)this.lineWidth.getValue());
            if (Thunderhack.friendManager.isFriend(e.getName())) {
                GlStateManager.color(this.Color3.getValue().getRed() / 255.0f, this.Color3.getValue().getGreen() / 255.0f, this.Color3.getValue().getBlue() / 255.0f, this.Color3.getValue().getAlpha() / 255.0f);
            }
            else {
                GlStateManager.color(this.Color3.getValue().getRed() / 255.0f, this.Color3.getValue().getGreen() / 255.0f, this.Color3.getValue().getBlue() / 255.0f, this.Color3.getValue().getAlpha() / 255.0f);
            }
            final Vec3d vec = this.getVec3(event, e);
            final double x = vec.x - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosX();
            final double y = vec.y - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosY();
            final double z = vec.z - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosZ();
            GL11.glTranslated(x, y, z);
            final float xOff = e.prevRenderYawOffset + (e.renderYawOffset - e.prevRenderYawOffset) * event.getPartialTicks();
            GL11.glRotatef(-xOff, 0.0f, 1.0f, 0.0f);
            GL11.glTranslated(0.0, 0.0, e.isSneaking() ? -0.235 : 0.0);
            final float yOff = e.isSneaking() ? 0.6f : 0.75f;
            GL11.glPushMatrix();
            GL11.glTranslated(-0.125, (double)yOff, 0.0);
            if (entPos[3][0] != 0.0f) {
                GL11.glRotatef(entPos[3][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (entPos[3][1] != 0.0f) {
                GL11.glRotatef(entPos[3][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (entPos[3][2] != 0.0f) {
                GL11.glRotatef(entPos[3][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, (double)(-yOff), 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.125, (double)yOff, 0.0);
            if (entPos[4][0] != 0.0f) {
                GL11.glRotatef(entPos[4][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (entPos[4][1] != 0.0f) {
                GL11.glRotatef(entPos[4][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (entPos[4][2] != 0.0f) {
                GL11.glRotatef(entPos[4][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, (double)(-yOff), 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glTranslated(0.0, 0.0, e.isSneaking() ? 0.25 : 0.0);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, e.isSneaking() ? -0.05 : 0.0, e.isSneaking() ? -0.01725 : 0.0);
            GL11.glPushMatrix();
            GL11.glTranslated(-0.375, yOff + 0.55, 0.0);
            if (entPos[1][0] != 0.0f) {
                GL11.glRotatef(entPos[1][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (entPos[1][1] != 0.0f) {
                GL11.glRotatef(entPos[1][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (entPos[1][2] != 0.0f) {
                GL11.glRotatef(-entPos[1][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, -0.5, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.375, yOff + 0.55, 0.0);
            if (entPos[2][0] != 0.0f) {
                GL11.glRotatef(entPos[2][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (entPos[2][1] != 0.0f) {
                GL11.glRotatef(entPos[2][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (entPos[2][2] != 0.0f) {
                GL11.glRotatef(-entPos[2][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, -0.5, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glRotatef(xOff - e.rotationYawHead, 0.0f, 1.0f, 0.0f);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, yOff + 0.55, 0.0);
            if (entPos[0][0] != 0.0f) {
                GL11.glRotatef(entPos[0][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, 0.3, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            GL11.glRotatef(e.isSneaking() ? 25.0f : 0.0f, 1.0f, 0.0f, 0.0f);
            GL11.glTranslated(0.0, e.isSneaking() ? -0.16175 : 0.0, e.isSneaking() ? -0.48025 : 0.0);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, (double)yOff, 0.0);
            GL11.glBegin(3);
            GL11.glVertex3d(-0.125, 0.0, 0.0);
            GL11.glVertex3d(0.125, 0.0, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, (double)yOff, 0.0);
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, 0.55, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, yOff + 0.55, 0.0);
            GL11.glBegin(3);
            GL11.glVertex3d(-0.375, 0.0, 0.0);
            GL11.glVertex3d(0.375, 0.0, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
    }
    
    private void startEnd(final boolean revert) {
        if (revert) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GL11.glEnable(2848);
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GL11.glHint(3154, 4354);
        }
        else {
            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GL11.glDisable(2848);
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }
        GlStateManager.depthMask(!revert);
    }
    
    private boolean doesntContain(final EntityPlayer entityPlayer) {
        return !Skeleton.mc.world.playerEntities.contains(entityPlayer);
    }
    
    static {
        entities = new HashMap<EntityPlayer, float[][]>();
    }
}
