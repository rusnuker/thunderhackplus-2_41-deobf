//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.math.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.mixin.mixins.*;

public class Flight extends Module
{
    public boolean pendingFlagApplyPacket;
    private final Setting<Mode> mode;
    public Setting<Float> speed;
    public Setting<Float> speedValue;
    public Setting<Float> vspeedValue;
    public Setting<Boolean> spoofValue;
    public Setting<Boolean> aboba;
    private double lastMotionX;
    private double lastMotionY;
    private double lastMotionZ;
    
    public Flight() {
        super("Flight", "Makes you fly.", Module.Category.MOVEMENT);
        this.pendingFlagApplyPacket = false;
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.Vanilla));
        this.speed = (Setting<Float>)this.register(new Setting("Speed", (T)0.1f, (T)0.0f, (T)10.0f, v -> this.mode.getValue() == Mode.Vanilla));
        this.speedValue = (Setting<Float>)this.register(new Setting("Speed", (T)1.69f, (T)0.0f, (T)5.0f, v -> this.mode.getValue() == Mode.MatrixJump));
        this.vspeedValue = (Setting<Float>)this.register(new Setting("Vertical", (T)0.78f, (T)0.0f, (T)5.0f, v -> this.mode.getValue() == Mode.MatrixJump));
        this.spoofValue = (Setting<Boolean>)this.register(new Setting("Ground", (T)false, v -> this.mode.getValue() == Mode.MatrixJump));
        this.aboba = (Setting<Boolean>)this.register(new Setting("AutoToggle", (T)false, v -> this.mode.getValue() == Mode.MatrixJump));
        this.lastMotionX = 0.0;
        this.lastMotionY = 0.0;
        this.lastMotionZ = 0.0;
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final EventSync event) {
        if (this.mode.getValue() == Mode.Vanilla) {
            Flight.mc.player.setVelocity(0.0, 0.0, 0.0);
            Flight.mc.player.jumpMovementFactor = this.speed.getValue();
            final double[] dir = MathUtil.directionSpeed(this.speed.getValue());
            if (Flight.mc.player.movementInput.moveStrafe != 0.0f || Flight.mc.player.movementInput.moveForward != 0.0f) {
                Flight.mc.player.motionX = dir[0];
                Flight.mc.player.motionZ = dir[1];
            }
            else {
                Flight.mc.player.motionX = 0.0;
                Flight.mc.player.motionZ = 0.0;
            }
            if (Flight.mc.gameSettings.keyBindJump.isKeyDown()) {
                final EntityPlayerSP player = Flight.mc.player;
                player.motionY += this.speed.getValue();
            }
            if (Flight.mc.gameSettings.keyBindSneak.isKeyDown()) {
                final EntityPlayerSP player2 = Flight.mc.player;
                player2.motionY -= this.speed.getValue();
            }
        }
        else if (this.mode.getValue() == Mode.AirJump && MovementUtil.isMoving() && !Flight.mc.world.getCollisionBoxes((Entity)Flight.mc.player, Flight.mc.player.getEntityBoundingBox().expand(0.5, 0.0, 0.5).offset(0.0, -1.0, 0.0)).isEmpty()) {
            Flight.mc.player.onGround = true;
            Flight.mc.player.jump();
        }
    }
    
    public void onUpdate() {
        if (this.mode.getValue() != Mode.MatrixJump) {
            return;
        }
        Flight.mc.player.capabilities.isFlying = false;
        Flight.mc.player.motionX = 0.0;
        Flight.mc.player.motionY = 0.0;
        Flight.mc.player.motionZ = 0.0;
        if (Flight.mc.gameSettings.keyBindJump.isKeyDown()) {
            final EntityPlayerSP player = Flight.mc.player;
            player.motionY += this.vspeedValue.getValue();
        }
        if (Flight.mc.gameSettings.keyBindSneak.isKeyDown()) {
            final EntityPlayerSP player2 = Flight.mc.player;
            player2.motionY -= this.vspeedValue.getValue();
        }
        LongJump.strafe(this.speedValue.getValue());
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (this.mode.getValue() != Mode.MatrixJump) {
            return;
        }
        if (fullNullCheck()) {
            return;
        }
        if (e.getPacket() instanceof SPacketPlayerPosLook) {
            this.pendingFlagApplyPacket = true;
            this.lastMotionX = Flight.mc.player.motionX;
            this.lastMotionY = Flight.mc.player.motionY;
            this.lastMotionZ = Flight.mc.player.motionZ;
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send e) {
        if (this.mode.getValue() == Mode.MatrixJump) {
            if (e.getPacket() instanceof CPacketPlayer.PositionRotation && this.pendingFlagApplyPacket) {
                Flight.mc.player.motionX = this.lastMotionX;
                Flight.mc.player.motionY = this.lastMotionY;
                Flight.mc.player.motionZ = this.lastMotionZ;
                this.pendingFlagApplyPacket = false;
                if (this.aboba.getValue()) {
                    this.toggle();
                }
            }
            if (e.getPacket() instanceof CPacketPlayer) {
                final CPacketPlayer packet = (CPacketPlayer)e.getPacket();
                if (this.spoofValue.getValue()) {
                    ((ICPacketPlayer)packet).setOnGround(true);
                }
            }
        }
        else if (this.mode.getValue() == Mode.AirJump) {
            if (fullNullCheck()) {
                return;
            }
            if (e.getPacket() instanceof SPacketPlayerPosLook) {
                this.toggle();
            }
        }
    }
    
    private enum Mode
    {
        Vanilla, 
        MatrixJump, 
        AirJump;
    }
}
