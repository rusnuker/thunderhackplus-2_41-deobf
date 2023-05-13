//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.util.math.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraft.client.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraftforge.client.event.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.util.math.*;

public class ElytraFly2b2tNew extends Module
{
    private final Setting<Float> speedSetting;
    public Setting<Boolean> timerControl;
    public Setting<Boolean> durabilityWarning;
    public Setting<Boolean> glide;
    private final Setting<Float> glideSpeed;
    public Setting<Float> speed;
    public Setting<Float> speedM;
    public Setting<Integer> acceleration;
    public Setting<Float> boost_delay;
    int acceleration_ticks;
    double current_speed;
    private boolean elytraIsEquipped;
    private int elytraDurability;
    private boolean isFlying;
    private boolean isStandingStillH;
    private double hoverTarget;
    private boolean hoverState;
    private final Timer accelerationDelay;
    private float dYaw;
    private float dPitch;
    
    public ElytraFly2b2tNew() {
        super("ElytraFly2b2tNew", "ElytraFly2b2tNew", Module.Category.MOVEMENT);
        this.speedSetting = (Setting<Float>)this.register(new Setting("FSpeed", (T)16.0f, (T)0.1f, (T)20.0f));
        this.timerControl = (Setting<Boolean>)this.register(new Setting("Timer", (T)true));
        this.durabilityWarning = (Setting<Boolean>)this.register(new Setting("ToggleIfLow", (T)true));
        this.glide = (Setting<Boolean>)this.register(new Setting("Glide", (T)false));
        this.glideSpeed = (Setting<Float>)this.register(new Setting("GlideSpeed", (T)1.0f, (T)0.1f, (T)10.0f, v -> this.glide.getValue()));
        this.speed = (Setting<Float>)this.register(new Setting("Speed", (T)0.8f, (T)0.1f, (T)5.0f));
        this.speedM = (Setting<Float>)this.register(new Setting("MaxSpeed", (T)0.8f, (T)0.1f, (T)5.0f));
        this.acceleration = (Setting<Integer>)this.register(new Setting("Boost", (T)60, (T)0, (T)100));
        this.boost_delay = (Setting<Float>)this.register(new Setting("BoostDelay", (T)1.5f, (T)0.1f, (T)3.0f));
        this.acceleration_ticks = 0;
        this.elytraIsEquipped = false;
        this.elytraDurability = 0;
        this.isFlying = false;
        this.isStandingStillH = false;
        this.hoverTarget = -1.0;
        this.hoverState = false;
        this.accelerationDelay = new Timer();
        this.dYaw = 0.0f;
        this.dPitch = 0.0f;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (fullNullCheck()) {
            return;
        }
        if (ElytraFly2b2tNew.mc.player.isSpectator() || !this.elytraIsEquipped || this.elytraDurability <= 1 || !this.isFlying) {
            return;
        }
        if (e.getPacket() instanceof SPacketPlayerPosLook) {
            final SPacketPlayerPosLook packet = (SPacketPlayerPosLook)e.getPacket();
            ((ISPacketPlayerPosLook)packet).setPitch(ElytraFly2b2tNew.mc.player.rotationPitch);
            this.acceleration_ticks = 0;
            this.accelerationDelay.reset();
        }
    }
    
    public void flyyyyy() {
        final double[] dir = MathUtil.directionSpeed((float)RenderUtil.interpolate(this.speedM.getValue(), this.speed.getValue(), Math.min(this.acceleration_ticks, this.acceleration.getValue()) / (float)this.acceleration.getValue()));
        if (Flight.mc.player.movementInput.moveStrafe != 0.0f || Flight.mc.player.movementInput.moveForward != 0.0f) {
            Flight.mc.player.motionX = dir[0];
            Flight.mc.player.motionZ = dir[1];
        }
        if (this.glideSpeed.getValue() != 0.0 && !ElytraFly2b2tNew.mc.gameSettings.keyBindJump.isKeyDown() && !ElytraFly2b2tNew.mc.gameSettings.keyBindSneak.isKeyDown()) {
            ElytraFly2b2tNew.mc.player.motionY = -this.glideSpeed.getValue();
        }
    }
    
    @SubscribeEvent
    public void onElytra(final EventPlayerTravel event) {
        if (ElytraFly2b2tNew.mc.player.isSpectator()) {
            return;
        }
        this.stateUpdate();
        this.flyyyyy();
        if (this.elytraIsEquipped && this.elytraDurability > 1) {
            if (!this.isFlying) {
                this.takeoff();
            }
            else {
                Thunderhack.TICK_TIMER = 1.0f;
                ElytraFly2b2tNew.mc.player.setSprinting(false);
                this.controlMode(event);
            }
        }
        else {
            this.reset2(true);
        }
        if (this.accelerationDelay.passedMs((long)(this.boost_delay.getValue() * 1000.0f))) {
            ++this.acceleration_ticks;
        }
    }
    
    public void stateUpdate() {
        final ItemStack armorSlot = (ItemStack)ElytraFly2b2tNew.mc.player.inventory.armorInventory.get(2);
        this.elytraIsEquipped = (armorSlot.getItem() == Items.ELYTRA);
        if (this.elytraIsEquipped) {
            final int oldDurability = this.elytraDurability;
            this.elytraDurability = armorSlot.getMaxDamage() - armorSlot.getItemDamage();
            if (!ElytraFly2b2tNew.mc.player.onGround && oldDurability != this.elytraDurability && this.elytraDurability <= 1 && this.durabilityWarning.getValue()) {
                ElytraFly2b2tNew.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f));
                Command.sendMessage("Elytra is low, disabling!");
                this.toggle();
            }
        }
        else {
            this.elytraDurability = 0;
        }
        this.isFlying = ElytraFly2b2tNew.mc.player.isElytraFlying();
        this.isStandingStillH = (ElytraFly2b2tNew.mc.player.movementInput.moveForward == 0.0f && ElytraFly2b2tNew.mc.player.movementInput.moveStrafe == 0.0f);
        if (this.shouldSwing()) {
            ElytraFly2b2tNew.mc.player.prevLimbSwingAmount = ElytraFly2b2tNew.mc.player.limbSwingAmount;
            final EntityPlayerSP player = ElytraFly2b2tNew.mc.player;
            player.limbSwing += (float)1.3;
            final float speedRatio = (float)(this.current_speed / (float)RenderUtil.interpolate(this.speedM.getValue(), this.speed.getValue(), Math.min(this.acceleration_ticks, this.acceleration.getValue()) / (float)this.acceleration.getValue()));
            final EntityPlayerSP player2 = ElytraFly2b2tNew.mc.player;
            player2.limbSwingAmount += (float)((speedRatio * 1.2 - ElytraFly2b2tNew.mc.player.limbSwingAmount) * 0.4000000059604645);
        }
    }
    
    @SubscribeEvent
    public void updateValues(final EventSync e) {
        final double distTraveledLastTickX = ElytraFly2b2tNew.mc.player.posX - ElytraFly2b2tNew.mc.player.prevPosX;
        final double distTraveledLastTickZ = ElytraFly2b2tNew.mc.player.posZ - ElytraFly2b2tNew.mc.player.prevPosZ;
        this.current_speed = Math.sqrt(distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ);
    }
    
    private void reset2(final boolean cancelflu) {
        Thunderhack.TICK_TIMER = 1.0f;
        this.acceleration_ticks = 0;
        this.accelerationDelay.reset();
    }
    
    private void takeoff() {
        if (ElytraFly2b2tNew.mc.player.onGround) {
            this.reset2(ElytraFly2b2tNew.mc.player.onGround);
            return;
        }
        if (ElytraFly2b2tNew.mc.player.motionY < 0.0) {
            if (this.timerControl.getValue() && !ElytraFly2b2tNew.mc.isSingleplayer()) {
                Thunderhack.TICK_TIMER = 0.1f;
            }
            ElytraFly2b2tNew.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFly2b2tNew.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            this.hoverTarget = (float)(ElytraFly2b2tNew.mc.player.posY + 0.2);
        }
    }
    
    private void setSpeed(final double yaw) {
        ElytraFly2b2tNew.mc.player.setVelocity(Math.sin(-yaw) * (float)RenderUtil.interpolate(this.speedM.getValue(), this.speed.getValue(), Math.min(this.acceleration_ticks, this.acceleration.getValue()) / (float)this.acceleration.getValue()), ElytraFly2b2tNew.mc.player.motionY, Math.cos(yaw) * (float)RenderUtil.interpolate(this.speedM.getValue(), this.speed.getValue(), Math.min(this.acceleration_ticks, this.acceleration.getValue()) / (float)this.acceleration.getValue()));
    }
    
    private void controlMode(final EventPlayerTravel event) {
        final double currentSpeed = Math.sqrt(ElytraFly2b2tNew.mc.player.motionX * ElytraFly2b2tNew.mc.player.motionX + ElytraFly2b2tNew.mc.player.motionZ * ElytraFly2b2tNew.mc.player.motionZ);
        if (this.hoverTarget < 0.0) {
            this.hoverTarget = ElytraFly2b2tNew.mc.player.posY;
        }
        this.hoverState = this.getHoverState();
        Label_0147: {
            if (!this.isStandingStillH) {
                if (this.hoverState) {
                    if (currentSpeed >= 0.8) {
                        break Label_0147;
                    }
                    if (ElytraFly2b2tNew.mc.player.motionY > 1.0) {
                        break Label_0147;
                    }
                }
                ElytraFly2b2tNew.mc.player.motionY = -3.0E-14;
                this.setSpeed(this.calcMoveYaw());
            }
            else {
                ElytraFly2b2tNew.mc.player.setVelocity(0.0, 0.0, 0.0);
            }
        }
        event.setCanceled(true);
    }
    
    private boolean shouldSwing() {
        return this.isFlying;
    }
    
    @SubscribeEvent
    public void Skid(final EventSync e) {
        ElytraFly2b2tNew.mc.player.rotationPitch = -2.3f;
    }
    
    public boolean getHoverState() {
        return this.hoverState && ElytraFly2b2tNew.mc.player.posY < this.hoverTarget;
    }
    
    public void onDisable() {
        this.reset2(true);
        ElytraFly2b2tNew.mc.player.capabilities.isFlying = false;
        ElytraFly2b2tNew.mc.player.capabilities.setFlySpeed(0.05f);
    }
    
    public double calcMoveYaw() {
        double strafe = 90.0f * ElytraFly2b2tNew.mc.player.moveStrafing;
        strafe *= ((ElytraFly2b2tNew.mc.player.moveForward != 0.0f) ? (ElytraFly2b2tNew.mc.player.moveForward * 0.5f) : 1.0);
        double yaw = ElytraFly2b2tNew.mc.player.rotationYaw - strafe;
        yaw -= ((ElytraFly2b2tNew.mc.player.moveForward < 0.0f) ? 180.0 : 0.0);
        return Math.toRadians(yaw);
    }
    
    @SubscribeEvent
    public void onCameraSetup(final EntityViewRenderEvent.CameraSetup event) {
        if (PyroSpeed.isMovingClient()) {
            event.setYaw(event.getYaw() + this.dYaw);
            event.setPitch(event.getPitch() + this.dPitch);
        }
        else {
            this.dYaw = 0.0f;
            this.dPitch = 0.0f;
        }
    }
    
    @SubscribeEvent
    public void onTurnEvent(final TurnEvent event) {
        if (PyroSpeed.isMovingClient()) {
            this.dYaw += (float)(event.getYaw() * 0.15);
            this.dPitch -= (float)(event.getPitch() * 0.15);
            this.dPitch = MathHelper.clamp(this.dPitch, -90.0f, 90.0f);
            event.setCanceled(true);
        }
        else {
            this.dYaw = 0.0f;
            this.dPitch = 0.0f;
        }
    }
    
    public void onEnable() {
        this.dYaw = 0.0f;
        this.dPitch = 0.0f;
    }
}
