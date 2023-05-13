//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import io.netty.util.internal.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;

public class BoatFly extends Module
{
    private final ConcurrentSet Field2263;
    public Setting<Boolean> strict;
    public Setting<Boolean> limit;
    public Setting<Boolean> phase;
    public Setting<Boolean> gravity;
    public Setting<Boolean> ongroundpacket;
    public Setting<Boolean> spoofpackets;
    public Setting<Boolean> cancelrotations;
    public Setting<Boolean> cancel;
    public Setting<Boolean> remount;
    public Setting<Boolean> stop;
    public Setting<Boolean> ylimit;
    public Setting<Boolean> debug;
    public Setting<Boolean> automount;
    public Setting<Boolean> stopunloaded;
    private final Setting<Mode> mode;
    private final Setting<Float> speed;
    private final Setting<Float> yspeed;
    private final Setting<Float> glidespeed;
    private final Setting<Float> timer;
    private final Setting<Float> height;
    private final Setting<Float> offset;
    private final Setting<Integer> enableticks;
    private final Setting<Integer> waitticks;
    private int Field2264;
    private int Field2265;
    private boolean Field2266;
    private boolean Field2267;
    private boolean Field2268;
    
    public BoatFly() {
        super("BoatFly", "BoatFly", Module.Category.MOVEMENT);
        this.Field2263 = new ConcurrentSet();
        this.strict = (Setting<Boolean>)this.register(new Setting("Strict", (T)false));
        this.limit = (Setting<Boolean>)this.register(new Setting("Limit", (T)true));
        this.phase = (Setting<Boolean>)this.register(new Setting("Phase", (T)true));
        this.gravity = (Setting<Boolean>)this.register(new Setting("Gravity", (T)true));
        this.ongroundpacket = (Setting<Boolean>)this.register(new Setting("OnGroundPacket", (T)false));
        this.spoofpackets = (Setting<Boolean>)this.register(new Setting("SpoofPackets", (T)false));
        this.cancelrotations = (Setting<Boolean>)this.register(new Setting("CancelRotations", (T)true));
        this.cancel = (Setting<Boolean>)this.register(new Setting("Cancel", (T)true));
        this.remount = (Setting<Boolean>)this.register(new Setting("Remount", (T)true));
        this.stop = (Setting<Boolean>)this.register(new Setting("Stop", (T)false));
        this.ylimit = (Setting<Boolean>)this.register(new Setting("yLimit", (T)false));
        this.debug = (Setting<Boolean>)this.register(new Setting("Debug", (T)true));
        this.automount = (Setting<Boolean>)this.register(new Setting("AutoMount", (T)true));
        this.stopunloaded = (Setting<Boolean>)this.register(new Setting("StopUnloaded", (T)true));
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.Packet));
        this.speed = (Setting<Float>)this.register(new Setting("Speed", (T)2.0f, (T)0.0f, (T)45.0f));
        this.yspeed = (Setting<Float>)this.register(new Setting("YSpeed", (T)1.0f, (T)0.0f, (T)10.0f));
        this.glidespeed = (Setting<Float>)this.register(new Setting("GlideSpeed", (T)1.0f, (T)0.0f, (T)10.0f));
        this.timer = (Setting<Float>)this.register(new Setting("Timer", (T)1.0f, (T)0.0f, (T)5.0f));
        this.height = (Setting<Float>)this.register(new Setting("Height", (T)127.0f, (T)0.0f, (T)256.0f));
        this.offset = (Setting<Float>)this.register(new Setting("Offset", (T)0.1f, (T)0.0f, (T)10.0f));
        this.enableticks = (Setting<Integer>)this.register(new Setting("EnableTicks", (T)10, (T)1, (T)100));
        this.waitticks = (Setting<Integer>)this.register(new Setting("WaitTicks", (T)10, (T)1, (T)100));
        this.Field2264 = 0;
        this.Field2265 = 0;
        this.Field2266 = false;
        this.Field2267 = false;
        this.Field2268 = false;
    }
    
    public static double[] Method1330(final double d) {
        float f = Util.mc.player.movementInput.moveForward;
        float f2 = Util.mc.player.movementInput.moveStrafe;
        float f3 = Util.mc.player.prevRotationYaw + (Util.mc.player.rotationYaw - Util.mc.player.prevRotationYaw) * Util.mc.getRenderPartialTicks();
        if (f != 0.0f) {
            if (f2 > 0.0f) {
                f3 += ((f > 0.0f) ? -45 : 45);
            }
            else if (f2 < 0.0f) {
                f3 += ((f > 0.0f) ? 45 : -45);
            }
            f2 = 0.0f;
            if (f > 0.0f) {
                f = 1.0f;
            }
            else if (f < 0.0f) {
                f = -1.0f;
            }
        }
        final double d2 = Math.sin(Math.toRadians(f3 + 90.0f));
        final double d3 = Math.cos(Math.toRadians(f3 + 90.0f));
        final double d4 = f * d * d3 + f2 * d * d2;
        final double d5 = f * d * d2 - f2 * d * d3;
        return new double[] { d4, d5 };
    }
    
    public void onEnable() {
        if (Util.mc.player == null || Util.mc.player.world == null) {
            this.toggle();
            return;
        }
        if (this.automount.getValue()) {
            this.Method2868();
        }
    }
    
    public void onDisable() {
        Thunderhack.TICK_TIMER = 1.0f;
        this.Field2263.clear();
        this.Field2266 = false;
        if (Util.mc.player == null) {
            return;
        }
        if (this.phase.getValue() && this.mode.getValue() == Mode.Motion) {
            if (Util.mc.player.getRidingEntity() != null) {
                Util.mc.player.getRidingEntity().noClip = false;
            }
            Util.mc.player.noClip = false;
        }
        if (Util.mc.player.getRidingEntity() != null) {
            Util.mc.player.getRidingEntity().setNoGravity(false);
        }
        Util.mc.player.setNoGravity(false);
    }
    
    private float Method2874() {
        this.Field2268 = !this.Field2268;
        return this.Field2268 ? this.offset.getValue() : (-this.offset.getValue());
    }
    
    private void Method2875(final CPacketVehicleMove cPacketVehicleMove) {
        this.Field2263.add((Object)cPacketVehicleMove);
        Util.mc.player.connection.sendPacket((Packet)cPacketVehicleMove);
    }
    
    private void Method2876(final Entity entity) {
        final double d = entity.posY;
        BlockPos blockPos = new BlockPos(entity.posX, (double)(int)entity.posY, entity.posZ);
        for (int i = 0; i < 255; ++i) {
            if (!Util.mc.world.getBlockState(blockPos).getMaterial().isReplaceable() || Util.mc.world.getBlockState(blockPos).getBlock() == Blocks.WATER) {
                entity.posY = blockPos.getY() + 1;
                if (this.debug.getValue()) {
                    Command.sendMessage("GroundY" + entity.posY);
                }
                this.Method2875(new CPacketVehicleMove(entity));
                entity.posY = d;
                break;
            }
            blockPos = blockPos.add(0, -1, 0);
        }
    }
    
    private void Method2868() {
        for (final Entity entity : Util.mc.world.loadedEntityList) {
            if (entity instanceof EntityBoat) {
                if (Util.mc.player.getDistance(entity) >= 5.0f) {
                    continue;
                }
                Util.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity, EnumHand.MAIN_HAND));
                break;
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerTravel(final EventPlayerTravel eventPlayerTravel) {
        if (Util.mc.player == null || Util.mc.world == null) {
            return;
        }
        if (Util.mc.player.getRidingEntity() == null) {
            if (this.automount.getValue()) {
                this.Method2868();
            }
            return;
        }
        if (this.phase.getValue() && this.mode.getValue() == Mode.Motion) {
            Util.mc.player.getRidingEntity().noClip = true;
            Util.mc.player.getRidingEntity().setNoGravity(true);
            Util.mc.player.noClip = true;
        }
        if (!this.Field2267) {
            Util.mc.player.getRidingEntity().setNoGravity(!this.gravity.getValue());
            Util.mc.player.setNoGravity(!this.gravity.getValue());
        }
        if (this.stop.getValue()) {
            if (this.Field2264 > this.enableticks.getValue() && !this.Field2266) {
                this.Field2264 = 0;
                this.Field2266 = true;
                this.Field2265 = this.waitticks.getValue();
            }
            if (this.Field2265 > 0 && this.Field2266) {
                --this.Field2265;
                return;
            }
            if (this.Field2265 <= 0) {
                this.Field2266 = false;
            }
        }
        final Entity entity = Util.mc.player.getRidingEntity();
        if (this.debug.getValue()) {
            Command.sendMessage("Y" + entity.posY);
            Command.sendMessage("Fall" + entity.fallDistance);
        }
        if ((!Util.mc.world.isChunkGeneratedAt(entity.getPosition().getX() >> 4, entity.getPosition().getZ() >> 4) || entity.getPosition().getY() < 0) && this.stopunloaded.getValue()) {
            if (this.debug.getValue()) {
                Command.sendMessage("Detected unloaded chunk!");
            }
            this.Field2267 = true;
            return;
        }
        if (this.timer.getValue() != 1.0f) {
            Thunderhack.TICK_TIMER = this.timer.getValue();
        }
        entity.rotationYaw = Util.mc.player.rotationYaw;
        final double[] dArray = Method1330(this.speed.getValue());
        final double d = entity.posX + dArray[0];
        final double d2 = entity.posZ + dArray[1];
        double d3 = entity.posY;
        if ((!Util.mc.world.isChunkGeneratedAt((int)d >> 4, (int)d2 >> 4) || entity.getPosition().getY() < 0) && this.stopunloaded.getValue()) {
            if (this.debug.getValue()) {
                Command.sendMessage("Detected unloaded chunk!");
            }
            this.Field2267 = true;
            return;
        }
        this.Field2267 = false;
        entity.motionY = -(this.glidespeed.getValue() / 100.0f);
        if (this.mode.getValue() == Mode.Motion) {
            entity.motionX = dArray[0];
            entity.motionZ = dArray[1];
        }
        if (Util.mc.player.movementInput.jump) {
            if (!this.ylimit.getValue() || entity.posY <= this.height.getValue()) {
                if (this.mode.getValue() == Mode.Motion) {
                    final Entity entity2 = entity;
                    entity2.motionY += this.yspeed.getValue();
                }
                else {
                    d3 += this.yspeed.getValue();
                }
            }
        }
        else if (Util.mc.player.movementInput.sneak) {
            if (this.mode.getValue() == Mode.Motion) {
                final Entity entity3 = entity;
                entity3.motionY += -this.yspeed.getValue();
            }
            else {
                d3 += -this.yspeed.getValue();
            }
        }
        if (Util.mc.player.movementInput.moveStrafe == 0.0f && Util.mc.player.movementInput.moveForward == 0.0f) {
            entity.motionX = 0.0;
            entity.motionZ = 0.0;
        }
        if (this.ongroundpacket.getValue()) {
            this.Method2876(entity);
        }
        if (this.mode.getValue() != Mode.Motion) {
            entity.setPosition(d, d3, d2);
        }
        if (this.mode.getValue() == Mode.Packet) {
            this.Method2875(new CPacketVehicleMove(entity));
        }
        if (this.strict.getValue()) {
            Util.mc.player.connection.sendPacket((Packet)new CPacketClickWindow(0, 0, 0, ClickType.CLONE, ItemStack.EMPTY, (short)0));
        }
        if (this.spoofpackets.getValue()) {
            final Vec3d vec3d = entity.getPositionVector().add(0.0, (double)this.Method2874(), 0.0);
            final EntityBoat entityBoat = new EntityBoat((World)Util.mc.world, vec3d.x, vec3d.y, vec3d.z);
            entityBoat.rotationYaw = entity.rotationYaw;
            entityBoat.rotationPitch = entity.rotationPitch;
            this.Method2875(new CPacketVehicleMove((Entity)entityBoat));
        }
        if (this.remount.getValue()) {
            Util.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity, EnumHand.MAIN_HAND));
        }
        eventPlayerTravel.setCanceled(true);
        ++this.Field2264;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive eventNetworkPrePacketEvent) {
        if (fullNullCheck()) {
            return;
        }
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketDisconnect) {
            this.toggle();
        }
        if (!Util.mc.player.isRiding() || this.Field2267 || this.Field2266) {
            return;
        }
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketMoveVehicle && Util.mc.player.isRiding() && this.cancel.getValue()) {
            eventNetworkPrePacketEvent.setCanceled(true);
        }
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketPlayerPosLook && Util.mc.player.isRiding() && this.cancel.getValue()) {
            eventNetworkPrePacketEvent.setCanceled(true);
        }
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketEntity && this.cancel.getValue()) {
            eventNetworkPrePacketEvent.setCanceled(true);
        }
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketEntityAttach && this.cancel.getValue()) {
            eventNetworkPrePacketEvent.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send eventNetworkPostPacketEvent) {
        if (Util.mc.player == null || Util.mc.world == null) {
            return;
        }
        if (((eventNetworkPostPacketEvent.getPacket() instanceof CPacketPlayer.Rotation && this.cancelrotations.getValue()) || eventNetworkPostPacketEvent.getPacket() instanceof CPacketInput) && Util.mc.player.isRiding()) {
            eventNetworkPostPacketEvent.setCanceled(true);
        }
        if (this.Field2267 && eventNetworkPostPacketEvent.getPacket() instanceof CPacketVehicleMove) {
            eventNetworkPostPacketEvent.setCanceled(true);
        }
        if (!Util.mc.player.isRiding() || this.Field2267 || this.Field2266) {
            return;
        }
        final Entity entity = Util.mc.player.getRidingEntity();
        if ((!Util.mc.world.isChunkGeneratedAt(entity.getPosition().getX() >> 4, entity.getPosition().getZ() >> 4) || entity.getPosition().getY() < 0) && this.stopunloaded.getValue()) {
            return;
        }
        if (eventNetworkPostPacketEvent.getPacket() instanceof CPacketVehicleMove && this.limit.getValue() && this.mode.getValue() == Mode.Packet) {
            final CPacketVehicleMove cPacketVehicleMove = (CPacketVehicleMove)eventNetworkPostPacketEvent.getPacket();
            if (this.Field2263.contains((Object)cPacketVehicleMove)) {
                this.Field2263.remove((Object)cPacketVehicleMove);
            }
            else {
                eventNetworkPostPacketEvent.setCanceled(true);
            }
        }
    }
    
    public enum Mode
    {
        Packet, 
        Motion;
    }
}
