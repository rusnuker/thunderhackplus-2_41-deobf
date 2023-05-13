//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.funnygame;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.math.*;
import java.math.*;

public class GroundBoost extends Module
{
    public Setting<Integer> ticks;
    public Setting<Boolean> autoSprint;
    public Setting<Integer> spddd;
    public Setting<Boolean> usver;
    boolean hasact;
    private int rhh;
    private int stage;
    private double moveSpeed;
    private double distance;
    private float startY;
    
    public GroundBoost() {
        super("GroundBoost", "\u041b\u044e\u0442\u0435\u0439\u0448\u0438\u0435 \u0441\u043f\u0438\u0434\u044b-(\u043a\u0430\u043f\u043f\u0443\u0447\u0438\u043d\u043e+\u043f\u043b\u043e\u0441\u043a\u043e\u0441\u0442\u044c)", Category.FUNNYGAME);
        this.ticks = (Setting<Integer>)this.register(new Setting("RbandDelay", (T)2, (T)2, (T)40));
        this.autoSprint = (Setting<Boolean>)this.register(new Setting("AutoSprint", (T)true));
        this.spddd = (Setting<Integer>)this.register(new Setting("Speed", (T)2149, (T)50, (T)2149));
        this.usver = (Setting<Boolean>)this.register(new Setting("use", (T)false));
        this.hasact = false;
        this.rhh = 0;
        this.stage = 0;
        this.moveSpeed = 0.0;
        this.distance = 0.0;
        this.startY = 0.0f;
    }
    
    public static boolean isBoxColliding() {
        return Util.mc.world.getCollisionBoxes((Entity)Util.mc.player, Util.mc.player.getEntityBoundingBox().offset(0.0, 0.21, 0.0)).size() > 0;
    }
    
    public static double getJumpSpeed() {
        double defaultSpeed = 0.0;
        if (GroundBoost.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
            final int amplifier = GroundBoost.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier();
            defaultSpeed += (amplifier + 1) * 0.1;
        }
        return defaultSpeed;
    }
    
    @Override
    public void onDisable() {
        if (this.hasact) {
            GroundBoost.mc.gameSettings.viewBobbing = true;
        }
    }
    
    @Override
    public void onEnable() {
        try {
            if (GroundBoost.mc.gameSettings.viewBobbing) {
                this.hasact = true;
                GroundBoost.mc.gameSettings.viewBobbing = false;
            }
            this.stage = 2;
            this.distance = 0.0;
            this.moveSpeed = this.getBaseMoveSpeed();
            Thunderhack.TICK_TIMER = 1.0f;
            if (this.autoSprint.getValue() && GroundBoost.mc.player != null) {
                GroundBoost.mc.player.setSprinting(false);
            }
            this.startY = (float)GroundBoost.mc.player.posY;
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (e.getPacket() instanceof SPacketPlayerPosLook) {
            this.rhh = this.ticks.getValue();
            this.stage = 2;
            this.distance = 0.0;
            this.moveSpeed = this.getBaseMoveSpeed();
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send e) {
        if (e.getPacket() instanceof CPacketPlayer) {
            if (this.rhh > 0) {
                return;
            }
            final CPacketPlayer packet = (CPacketPlayer)e.getPacket();
            if (this.stage == 3) {
                ((ICPacketPlayer)packet).setY(packet.getY(0.0) + (isBoxColliding() ? 0.2 : 0.4) + getJumpSpeed());
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final EventSync e) {
        if (this.startY > GroundBoost.mc.player.posY) {
            this.startY = 0.0f;
            this.toggle();
        }
        final double d3 = GroundBoost.mc.player.posX - GroundBoost.mc.player.prevPosX;
        final double d4 = GroundBoost.mc.player.posZ - GroundBoost.mc.player.prevPosZ;
        this.distance = Math.sqrt(d3 * d3 + d4 * d4);
    }
    
    @SubscribeEvent
    public void onMoveEvent(final EventMove event) {
        if (fullNullCheck()) {
            return;
        }
        if (GroundBoost.mc.player.isElytraFlying() || GroundBoost.mc.player.fallDistance >= 4.0f) {
            return;
        }
        if (GroundBoost.mc.player.isInWater() || GroundBoost.mc.player.isInLava()) {
            return;
        }
        if (this.rhh > 0) {
            --this.rhh;
            return;
        }
        if (this.autoSprint.getValue()) {
            GroundBoost.mc.player.setSprinting(true);
        }
        if (!GroundBoost.mc.player.collidedHorizontally || this.checkMove()) {
            if (GroundBoost.mc.player.onGround) {
                if (this.stage == 2) {
                    if (this.rhh > 0) {
                        this.moveSpeed = this.getBaseMoveSpeed();
                    }
                    this.moveSpeed *= this.spddd.getValue() / 1000.0f;
                    this.stage = 3;
                }
                else if (this.stage == 3) {
                    final double var = 0.66 * (this.distance - this.getBaseMoveSpeed());
                    this.moveSpeed = this.distance - var;
                    this.stage = 2;
                }
            }
            this.setVanilaSpeed(event, this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed()));
        }
    }
    
    public double getBaseMoveSpeed() {
        if (GroundBoost.mc.player == null || GroundBoost.mc.world == null) {
            return 0.2873;
        }
        double d = 0.2873;
        if (GroundBoost.mc.player.isPotionActive(MobEffects.SPEED)) {
            final int n = GroundBoost.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            d *= 1.0 + 0.2 * (n + 1);
        }
        if (GroundBoost.mc.player.isPotionActive(MobEffects.JUMP_BOOST) && this.usver.getValue()) {
            final int n = GroundBoost.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier();
            d /= 1.0 + 0.2 * (n + 1);
        }
        return d;
    }
    
    public float[] setYaw(final float yaw, final double niggers) {
        float moveForward = GroundBoost.mc.player.movementInput.moveForward;
        float moveStrafe = GroundBoost.mc.player.movementInput.moveStrafe;
        float rotationYaw = yaw;
        if (moveForward == 0.0f && moveStrafe == 0.0f) {
            final float[] ret = { 0.0f, 0.0f };
            return ret;
        }
        if (moveForward != 0.0f) {
            if (moveStrafe >= 1.0f) {
                rotationYaw += ((moveForward > 0.0f) ? -45.0f : 45.0f);
                moveStrafe = 0.0f;
            }
            else if (moveStrafe <= -1.0f) {
                rotationYaw += ((moveForward > 0.0f) ? 45.0f : -45.0f);
                moveStrafe = 0.0f;
            }
            if (moveForward > 0.0f) {
                moveForward = 1.0f;
            }
            else if (moveForward < 0.0f) {
                moveForward = -1.0f;
            }
        }
        moveStrafe = MathUtil.clamp(moveStrafe, -1.0f, 1.0f);
        final double motionX = Math.cos(Math.toRadians(rotationYaw + 90.0f));
        final double motionZ = Math.sin(Math.toRadians(rotationYaw + 90.0f));
        final double newX = moveForward * niggers * motionX + moveStrafe * niggers * motionZ;
        final double newZ = moveForward * niggers * motionZ - moveStrafe * niggers * motionX;
        final float[] ret2 = { (float)newX, (float)newZ };
        return ret2;
    }
    
    public float[] getYaw(final double niggers) {
        final float yaw = GroundBoost.mc.player.prevRotationYaw + (GroundBoost.mc.player.rotationYaw - GroundBoost.mc.player.prevRotationYaw) * GroundBoost.mc.getRenderPartialTicks();
        return this.setYaw(yaw, niggers);
    }
    
    public double round(final double value, final int places) {
        final BigDecimal b = new BigDecimal(value).setScale(places, RoundingMode.HALF_UP);
        return b.doubleValue();
    }
    
    public boolean checkMove() {
        return GroundBoost.mc.player.moveForward != 0.0f || GroundBoost.mc.player.moveStrafing != 0.0f;
    }
    
    public void setVanilaSpeed(final EventMove event, final double speed) {
        float moveForward = GroundBoost.mc.player.movementInput.moveForward;
        float moveStrafe = GroundBoost.mc.player.movementInput.moveStrafe;
        float rotationYaw = GroundBoost.mc.player.rotationYaw;
        if (moveForward == 0.0f && moveStrafe == 0.0f) {
            event.set_x(0.0);
            event.set_z(0.0);
            return;
        }
        if (moveForward != 0.0f) {
            if (moveStrafe >= 1.0f) {
                rotationYaw += ((moveForward > 0.0f) ? -45.0f : 45.0f);
                moveStrafe = 0.0f;
            }
            else if (moveStrafe <= -1.0f) {
                rotationYaw += ((moveForward > 0.0f) ? 45.0f : -45.0f);
                moveStrafe = 0.0f;
            }
            if (moveForward > 0.0f) {
                moveForward = 1.0f;
            }
            else if (moveForward < 0.0f) {
                moveForward = -1.0f;
            }
        }
        moveStrafe = MathUtil.clamp(moveStrafe, -1.0f, 1.0f);
        final double motionX = Math.cos(Math.toRadians(rotationYaw + 90.0f));
        final double motionZ = Math.sin(Math.toRadians(rotationYaw + 90.0f));
        final double newX = moveForward * speed * motionX + moveStrafe * speed * motionZ;
        final double newZ = moveForward * speed * motionZ - moveStrafe * speed * motionX;
        event.set_x(newX);
        event.set_z(newZ);
        event.setCanceled(true);
    }
}
