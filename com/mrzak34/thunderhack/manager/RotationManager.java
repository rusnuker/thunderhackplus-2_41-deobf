//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import net.minecraftforge.common.*;
import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.*;

public class RotationManager
{
    private boolean blocking;
    private volatile float last_yaw;
    private volatile float last_pitch;
    public float visualYaw;
    public float visualPitch;
    public float prevVisualYaw;
    public float prevVisualPitch;
    private float yaw;
    private float pitch;
    
    public void init() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void unload() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onSync(final EventSync event) {
        if (Module.fullNullCheck()) {
            return;
        }
        this.yaw = Util.mc.player.rotationYaw;
        this.pitch = Util.mc.player.rotationPitch;
        this.prevVisualPitch = this.visualPitch;
        this.prevVisualYaw = this.visualYaw;
    }
    
    @SubscribeEvent
    public void postSync(final EventPostSync event) {
        if (Module.fullNullCheck()) {
            return;
        }
        this.visualPitch = Util.mc.player.rotationPitch;
        this.visualYaw = Util.mc.player.rotationYaw;
        Util.mc.player.rotationYaw = this.yaw;
        Util.mc.player.rotationYawHead = this.yaw;
        Util.mc.player.rotationPitch = this.pitch;
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.SendPost event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            this.readCPacket((CPacketPlayer)event.getPacket());
        }
        if (event.getPacket() instanceof CPacketPlayer.Position) {
            this.readCPacket((CPacketPlayer)event.getPacket());
        }
        if (event.getPacket() instanceof CPacketPlayer.Rotation) {
            this.readCPacket((CPacketPlayer)event.getPacket());
        }
        if (event.getPacket() instanceof CPacketPlayer.PositionRotation) {
            this.readCPacket((CPacketPlayer)event.getPacket());
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (Module.fullNullCheck()) {
            return;
        }
        if (e.getPacket() instanceof SPacketPlayerPosLook) {
            final SPacketPlayerPosLook packet = (SPacketPlayerPosLook)e.getPacket();
            float yaw = packet.getYaw();
            float pitch = packet.getPitch();
            if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.X_ROT)) {
                yaw += Util.mc.player.rotationYaw;
            }
            if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y_ROT)) {
                pitch += Util.mc.player.rotationPitch;
            }
            if (Util.mc.player != null) {
                this.setServerRotations(yaw, pitch);
            }
        }
    }
    
    public float getServerYaw() {
        return this.last_yaw;
    }
    
    public float getServerPitch() {
        return this.last_pitch;
    }
    
    public boolean isBlocking() {
        return this.blocking;
    }
    
    public void setBlocking(final boolean blocking) {
        this.blocking = blocking;
    }
    
    public void setServerRotations(final float yaw, final float pitch) {
        this.last_yaw = yaw;
        this.last_pitch = pitch;
    }
    
    public void readCPacket(final CPacketPlayer packetIn) {
        ((IEntityPlayerSP)Util.mc.player).setLastReportedYaw(packetIn.getYaw(((IEntityPlayerSP)Util.mc.player).getLastReportedYaw()));
        ((IEntityPlayerSP)Util.mc.player).setLastReportedPitch(packetIn.getPitch(((IEntityPlayerSP)Util.mc.player).getLastReportedPitch()));
        this.setServerRotations(packetIn.getYaw(this.last_yaw), packetIn.getPitch(this.last_pitch));
        Thunderhack.positionManager.setOnGround(packetIn.isOnGround());
    }
}
