//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import org.lwjgl.input.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.util.math.*;
import net.minecraft.client.*;

public class AimAssist extends Module
{
    public static EntityLivingBase target;
    public static int deltaX;
    public static int deltaY;
    public Setting<Boolean> players;
    public Setting<Float> strength;
    public Setting<Float> range;
    public Setting<Boolean> dead;
    public Setting<Boolean> invisibles;
    public Setting<Boolean> teams;
    public Setting<Boolean> nonPlayers;
    public Setting<Boolean> vertical;
    public Setting<Boolean> onlyClick;
    public Setting<Float> fov;
    private final Setting<sortEn> sort;
    
    public AimAssist() {
        super("AimAssist", "AimAssist", Category.COMBAT);
        this.players = (Setting<Boolean>)this.register(new Setting("Players", (T)true));
        this.strength = (Setting<Float>)this.register(new Setting("Strength", (T)40.0f, (T)1.0f, (T)50.0f));
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)6.0f, (T)0.1f, (T)10.0f));
        this.dead = (Setting<Boolean>)this.register(new Setting("Dead", (T)false));
        this.invisibles = (Setting<Boolean>)this.register(new Setting("Invisibles", (T)false));
        this.teams = (Setting<Boolean>)this.register(new Setting("Teams", (T)true));
        this.nonPlayers = (Setting<Boolean>)this.register(new Setting("NonPlayerslayers", (T)true));
        this.vertical = (Setting<Boolean>)this.register(new Setting("Vertical", (T)true));
        this.onlyClick = (Setting<Boolean>)this.register(new Setting("Clicking", (T)true));
        this.fov = (Setting<Float>)this.register(new Setting("FOV", (T)180.0f, (T)5.0f, (T)180.0f));
        this.sort = (Setting<sortEn>)this.register(new Setting("TargetMode", (T)sortEn.Distance));
    }
    
    public static boolean canSeeEntityAtFov(final Entity entityLiving, final float scope) {
        final double diffZ = entityLiving.posZ - AimAssist.mc.player.posZ;
        final double diffX = entityLiving.posX - AimAssist.mc.player.posX;
        final float yaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
        final double difference = angleDifference(yaw, AimAssist.mc.player.rotationYaw);
        return difference <= scope;
    }
    
    public static double angleDifference(final double a, final double b) {
        float yaw360 = (float)(Math.abs(a - b) % 360.0);
        if (yaw360 > 180.0f) {
            yaw360 = 360.0f - yaw360;
        }
        return yaw360;
    }
    
    @SubscribeEvent
    public void onPreMotion(final EventSync event) {
        AimAssist.target = this.getClosest(this.range.getValue());
        final float s = this.strength.getMax() - this.strength.getValue() + 1.0f;
        if (AimAssist.target == null || !AimAssist.mc.player.canEntityBeSeen((Entity)AimAssist.target)) {
            AimAssist.deltaX = (AimAssist.deltaY = 0);
            return;
        }
        final float[] rotations = this.getRotations();
        final float targetYaw = (float)(rotations[0] + Math.random());
        final float targetPitch = (float)(rotations[1] + Math.random());
        final float niggaYaw = (targetYaw - AimAssist.mc.player.rotationYaw) / Math.max(2.0f, s);
        final float niggaPitch = (targetPitch - AimAssist.mc.player.rotationPitch) / Math.max(2.0f, s);
        final float f = AimAssist.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        final float gcd = f * f * f * 8.0f;
        AimAssist.deltaX = Math.round(niggaYaw / gcd);
        if (this.vertical.getValue()) {
            AimAssist.deltaY = Math.round(niggaPitch / gcd);
        }
        else {
            AimAssist.deltaY = 0;
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (AimAssist.target == null) {
            return;
        }
        final float f = AimAssist.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        final float gcd = f * f * f * 8.0f;
        final int i = AimAssist.mc.gameSettings.invertMouse ? -1 : 1;
        final float f2 = (AimAssist.mc.mouseHelper.deltaX + (AimAssist.deltaX - AimAssist.mc.mouseHelper.deltaX)) * gcd;
        final float f3 = this.vertical.getValue() ? ((AimAssist.mc.mouseHelper.deltaY - (AimAssist.deltaY - AimAssist.mc.mouseHelper.deltaY)) * gcd) : 0.0f;
        if (!this.onlyClick.getValue() || (Mouse.isButtonDown(0) && AimAssist.mc.currentScreen == null)) {
            AimAssist.mc.player.rotationYaw += f2;
            AimAssist.mc.player.rotationPitch += f3 * i;
        }
    }
    
    @Override
    public void onDisable() {
        AimAssist.deltaX = 0;
        AimAssist.deltaY = 0;
    }
    
    private EntityLivingBase getClosest(final double range) {
        if (AimAssist.mc.world == null) {
            return null;
        }
        double dist = range;
        EntityLivingBase target = null;
        for (final Entity entity : AimAssist.mc.world.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                final EntityLivingBase livingBase = (EntityLivingBase)entity;
                if (!this.canAttack(livingBase)) {
                    continue;
                }
                final double currentDist = AimAssist.mc.player.getDistance((Entity)livingBase);
                if (currentDist > dist) {
                    continue;
                }
                dist = currentDist;
                target = livingBase;
            }
        }
        return target;
    }
    
    private boolean canAttack(final EntityLivingBase player) {
        return (!(player instanceof EntityPlayer) || this.players.getValue()) && canSeeEntityAtFov((Entity)player, this.fov.getValue() * 2.0f) && ((!(player instanceof EntityAnimal) && !(player instanceof EntityMob) && !(player instanceof EntityVillager)) || this.nonPlayers.getValue()) && (!player.isInvisible() || this.invisibles.getValue()) && (!player.isDead || this.dead.getValue()) && (!player.isOnSameTeam((Entity)AimAssist.mc.player) || !this.teams.getValue()) && player.ticksExisted >= 2 && !Thunderhack.friendManager.isFriend(player.getName()) && AimAssist.mc.player != player;
    }
    
    private float[] getRotations() {
        final double var4 = AimAssist.target.posX - (AimAssist.target.lastTickPosX - AimAssist.target.posX) + 0.01 - AimAssist.mc.player.posX;
        final double var5 = AimAssist.target.posZ - (AimAssist.target.lastTickPosZ - AimAssist.target.posZ) - AimAssist.mc.player.posZ;
        final double var6 = AimAssist.target.posY - (AimAssist.target.lastTickPosY - AimAssist.target.posY) + 0.4 + AimAssist.target.getEyeHeight() / 1.3 - (AimAssist.mc.player.posY + AimAssist.mc.player.getEyeHeight());
        final double var7 = MathHelper.sqrt(var4 * var4 + var5 * var5);
        float yaw = (float)(Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-(Math.atan2(var6, var7) * 180.0 / 3.141592653589793));
        yaw = AimAssist.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - AimAssist.mc.player.rotationYaw);
        pitch = AimAssist.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - AimAssist.mc.player.rotationPitch);
        final float[] rotations = { yaw, pitch };
        final float[] lastRotations = { yaw, pitch };
        final float[] fixedRotations = this.getFixedRotation(rotations, lastRotations);
        yaw = fixedRotations[0];
        pitch = fixedRotations[1];
        pitch = MathHelper.clamp(pitch, -90.0f, 90.0f);
        return new float[] { yaw, pitch };
    }
    
    public float[] getFixedRotation(final float[] rotations, final float[] lastRotations) {
        final Minecraft mc = Minecraft.getMinecraft();
        final float yaw = rotations[0];
        final float pitch = rotations[1];
        final float lastYaw = lastRotations[0];
        final float lastPitch = lastRotations[1];
        final float f = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        final float gcd = f * f * f * 1.2f;
        final float deltaYaw = yaw - lastYaw;
        final float deltaPitch = pitch + lastPitch;
        final float fixedDeltaYaw = deltaYaw - deltaYaw % gcd;
        final float fixedDeltaPitch = deltaPitch - deltaPitch % gcd;
        final float fixedYaw = lastYaw + fixedDeltaYaw;
        final float fixedPitch = lastPitch + fixedDeltaPitch;
        return new float[] { fixedYaw, fixedPitch };
    }
    
    public enum sortEn
    {
        Distance, 
        HigherArmor, 
        BlockingStatus, 
        LowestArmor, 
        Health, 
        Angle, 
        HurtTime;
    }
}
