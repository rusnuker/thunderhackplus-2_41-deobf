//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.setting.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.modules.movement.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.network.*;

public class RotationCanceller
{
    private final Timer timer;
    private final Setting<Integer> maxCancel;
    private final AutoCrystal module;
    private volatile CPacketPlayer last;
    
    public RotationCanceller(final AutoCrystal module, final Setting<Integer> maxCancel) {
        this.timer = new Timer();
        this.module = module;
        this.maxCancel = maxCancel;
    }
    
    public static CPacketPlayer positionRotation(final double x, final double y, final double z, final float yaw, final float pitch, final boolean onGround) {
        return (CPacketPlayer)new CPacketPlayer.PositionRotation(x, y, z, yaw, pitch, onGround);
    }
    
    public void onGameLoop() {
        if (this.last != null && this.timer.passedMs((int)this.maxCancel.getValue())) {
            this.sendLast();
        }
    }
    
    public synchronized void onPacketNigger(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            if (event.isCanceled() || ((PacketFly)Thunderhack.moduleManager.getModuleByClass((Class)PacketFly.class)).isEnabled()) {
                return;
            }
            this.reset();
            if (Thunderhack.rotationManager.isBlocking()) {
                return;
            }
            event.setCanceled(true);
            this.last = (CPacketPlayer)event.getPacket();
            this.timer.reset();
        }
    }
    
    public synchronized boolean setRotations(final RotationFunction function) {
        if (this.last == null) {
            return false;
        }
        final double x = this.last.getX(Thunderhack.positionManager.getX());
        final double y = this.last.getX(Thunderhack.positionManager.getY());
        final double z = this.last.getX(Thunderhack.positionManager.getZ());
        final float yaw = Thunderhack.rotationManager.getServerYaw();
        final float pitch = Thunderhack.rotationManager.getServerPitch();
        final boolean onGround = this.last.isOnGround();
        final ICPacketPlayer accessor = (ICPacketPlayer)this.last;
        final float[] r = function.apply(x, y, z, yaw, pitch);
        if (r[0] - yaw != 0.0 && r[1] - pitch != 0.0) {
            if (accessor.isRotating()) {
                accessor.setYaw(r[0]);
                accessor.setPitch(r[1]);
                this.sendLast();
            }
            else if (accessor.isMoving()) {
                this.last = positionRotation(x, y, z, r[0], r[1], onGround);
                this.sendLast();
            }
            else {
                this.last = Burrow.rotation(r[0], r[1], onGround);
                this.sendLast();
            }
            return true;
        }
        if (!accessor.isRotating() && !accessor.isMoving() && onGround == Thunderhack.positionManager.isOnGround()) {
            this.last = null;
            return true;
        }
        this.sendLast();
        return true;
    }
    
    public void reset() {
        if (this.last != null && Util.mc.player != null) {
            this.sendLast();
        }
    }
    
    public synchronized void drop() {
        this.last = null;
    }
    
    private synchronized void sendLast() {
        final CPacketPlayer packet = this.last;
        if (packet != null && Util.mc.player != null) {
            Util.mc.player.connection.sendPacket((Packet)packet);
            this.module.runPost();
        }
        this.last = null;
    }
    
    public void onPacketNigger9(final CPacketPlayer.Rotation rotation) {
        if (((PacketFly)Thunderhack.moduleManager.getModuleByClass((Class)PacketFly.class)).isEnabled()) {
            return;
        }
        this.reset();
        if (Thunderhack.rotationManager.isBlocking()) {
            return;
        }
        this.last = (CPacketPlayer)rotation;
        this.timer.reset();
    }
}
