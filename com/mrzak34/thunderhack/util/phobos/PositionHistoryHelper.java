//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.util.concurrent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import java.util.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.world.*;
import com.google.common.base.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.*;

public class PositionHistoryHelper
{
    private static final int REMOVE_TIME = 1000;
    private final Deque<RotationHistory> packets;
    
    public PositionHistoryHelper() {
        this.packets = new ConcurrentLinkedDeque<RotationHistory>();
    }
    
    @SubscribeEvent
    public void onConnect(final ConnectToServerEvent e) {
        this.packets.clear();
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send e) {
        if (e.getPacket() instanceof CPacketPlayer) {
            this.onPlayerPacket((CPacketPlayer)e.getPacket());
        }
        if (e.getPacket() instanceof CPacketPlayer.Position) {
            this.onPlayerPacket((CPacketPlayer)e.getPacket());
        }
        if (e.getPacket() instanceof CPacketPlayer.Rotation) {
            this.onPlayerPacket((CPacketPlayer)e.getPacket());
        }
        if (e.getPacket() instanceof CPacketPlayer.PositionRotation) {
            this.onPlayerPacket((CPacketPlayer)e.getPacket());
        }
    }
    
    private void onPlayerPacket(final CPacketPlayer packet) {
        this.packets.removeIf(h -> h == null || System.currentTimeMillis() - h.time > 1000L);
        this.packets.addFirst(new RotationHistory(packet));
    }
    
    public boolean arePreviousRotationsLegit(final Entity entity, final int time, final boolean skipFirst) {
        if (time == 0) {
            return true;
        }
        final Iterator<RotationHistory> itr = this.packets.iterator();
        while (itr.hasNext()) {
            final RotationHistory next = itr.next();
            if (skipFirst) {
                continue;
            }
            if (next == null) {
                continue;
            }
            if (System.currentTimeMillis() - next.time > 1000L) {
                itr.remove();
            }
            else {
                if (System.currentTimeMillis() - next.time > time) {
                    break;
                }
                if (!this.isLegit(next, entity)) {
                    return false;
                }
                continue;
            }
        }
        return true;
    }
    
    private boolean isLegit(final RotationHistory history, final Entity entity) {
        final RayTraceResult result = RayTracer.rayTraceEntities((World)Util.mc.world, (Entity)RotationUtil.getRotationPlayer(), 7.0, history.x, history.y, history.z, history.yaw, history.pitch, history.bb, (Predicate<Entity>)(e -> e != null && e.equals((Object)entity)), entity, entity);
        return result != null && entity.equals((Object)result.entityHit);
    }
    
    public Deque<RotationHistory> getPackets() {
        return this.packets;
    }
    
    public static final class RotationHistory
    {
        public final double x;
        public final double y;
        public final double z;
        public final float yaw;
        public final float pitch;
        public final long time;
        public final AxisAlignedBB bb;
        public final boolean hasLook;
        public final boolean hasPos;
        public final boolean hasChanged;
        
        public RotationHistory(final CPacketPlayer packet) {
            this(packet.getX(Thunderhack.positionManager.getX()), packet.getY(Thunderhack.positionManager.getY()), packet.getZ(Thunderhack.positionManager.getZ()), packet.getYaw(Thunderhack.rotationManager.getServerYaw()), packet.getPitch(Thunderhack.rotationManager.getServerPitch()), packet instanceof CPacketPlayer.Rotation || packet instanceof CPacketPlayer.PositionRotation, packet instanceof CPacketPlayer.Position || packet instanceof CPacketPlayer.PositionRotation);
        }
        
        public RotationHistory(final double x, final double y, final double z, final float yaw, final float pitch, final boolean hasLook, final boolean hasPos) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
            this.hasLook = hasLook;
            this.hasPos = hasPos;
            this.time = System.currentTimeMillis();
            final float w = Util.mc.player.width / 2.0f;
            final float h = Util.mc.player.height;
            this.bb = new AxisAlignedBB(x - w, y, z - w, x + w, y + h, z + w);
            this.hasChanged = (hasLook || hasPos);
        }
    }
}
