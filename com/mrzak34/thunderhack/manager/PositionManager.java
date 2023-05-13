//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import net.minecraftforge.common.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public class PositionManager
{
    private boolean blocking;
    private volatile int teleportID;
    private volatile double last_x;
    private volatile double last_y;
    private volatile double last_z;
    private volatile boolean onGround;
    private volatile boolean sprinting;
    
    public void init() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void unload() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.SendPost e) {
        if (e.getPacket() instanceof CPacketPlayer.Position) {
            this.readCPacket((CPacketPlayer)e.getPacket());
        }
        if (e.getPacket() instanceof CPacketPlayer.PositionRotation) {
            this.readCPacket((CPacketPlayer)e.getPacket());
        }
        if (e.getPacket() instanceof CPacketEntityAction) {
            final CPacketEntityAction action = (CPacketEntityAction)e.getPacket();
            if (action.getAction() == CPacketEntityAction.Action.START_SPRINTING) {
                this.sprinting = true;
            }
            if (action.getAction() == CPacketEntityAction.Action.STOP_SPRINTING) {
                this.sprinting = false;
            }
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (EventManager.fullNullCheck()) {
            return;
        }
        if (e.getPacket() instanceof SPacketPlayerPosLook) {
            final EntityPlayerSP player = Util.mc.player;
            if (player == null) {
                if (!Util.mc.isCallingFromMinecraftThread()) {
                    Util.mc.addScheduledTask(() -> this.onPacketReceive(e));
                }
                return;
            }
            final SPacketPlayerPosLook packet = (SPacketPlayerPosLook)e.getPacket();
            double x = packet.getX();
            double y = packet.getY();
            double z = packet.getZ();
            if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.X)) {
                x += player.posX;
            }
            if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y)) {
                y += player.posY;
            }
            if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Z)) {
                z += player.posZ;
            }
            this.last_x = MathHelper.clamp(x, -3.0E7, 3.0E7);
            this.last_y = y;
            this.last_z = MathHelper.clamp(z, -3.0E7, 3.0E7);
            player.serverPosX = EntityTracker.getPositionLong(this.last_x);
            player.serverPosY = EntityTracker.getPositionLong(this.last_y);
            player.serverPosZ = EntityTracker.getPositionLong(this.last_z);
            this.onGround = false;
            this.teleportID = packet.getTeleportId();
        }
    }
    
    public int getTeleportID() {
        return this.teleportID;
    }
    
    public double getX() {
        return this.last_x;
    }
    
    public double getY() {
        return this.last_y;
    }
    
    public double getZ() {
        return this.last_z;
    }
    
    public boolean isOnGround() {
        return this.onGround;
    }
    
    public void setOnGround(final boolean onGround) {
        this.onGround = onGround;
    }
    
    public AxisAlignedBB getBB() {
        final double x = this.last_x;
        final double y = this.last_y;
        final double z = this.last_z;
        final float w = Util.mc.player.width / 2.0f;
        final float h = Util.mc.player.height;
        return new AxisAlignedBB(x - w, y, z - w, x + w, y + h, z + w);
    }
    
    public Vec3d getVec() {
        return new Vec3d(this.last_x, this.last_y, this.last_z);
    }
    
    public void readCPacket(final CPacketPlayer packetIn) {
        this.last_x = packetIn.getX(Util.mc.player.posX);
        this.last_y = packetIn.getY(Util.mc.player.posY);
        this.last_z = packetIn.getZ(Util.mc.player.posZ);
        final EntityPlayer player;
        if ((player = (EntityPlayer)Util.mc.player) != null) {
            player.serverPosX = EntityTracker.getPositionLong(this.last_x);
            player.serverPosY = EntityTracker.getPositionLong(this.last_y);
            player.serverPosZ = EntityTracker.getPositionLong(this.last_z);
        }
        this.setOnGround(packetIn.isOnGround());
    }
    
    public double getDistanceSq(final Entity entity) {
        return this.getDistanceSq(entity.posX, entity.posY, entity.posZ);
    }
    
    public double getDistanceSq(final double x, final double y, final double z) {
        final double xDiff = this.last_x - x;
        final double yDiff = this.last_y - y;
        final double zDiff = this.last_z - z;
        return xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
    }
    
    public boolean canEntityBeSeen(final Entity entity) {
        return Util.mc.world.rayTraceBlocks(new Vec3d(this.last_x, this.last_y + Util.mc.player.getEyeHeight(), this.last_z), new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ), false, true, false) == null;
    }
    
    public void set(final double x, final double y, final double z) {
        this.last_x = x;
        this.last_y = y;
        this.last_z = z;
    }
    
    public boolean isBlocking() {
        return this.blocking;
    }
    
    public void setBlocking(final boolean blocking) {
        this.blocking = blocking;
    }
    
    public boolean isSprintingSS() {
        return this.sprinting;
    }
}
