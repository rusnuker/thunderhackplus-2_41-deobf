//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import io.netty.util.internal.*;
import java.util.*;
import java.util.concurrent.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.util.math.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.function.*;
import com.mrzak34.thunderhack.events.*;

public class PacketFly2 extends Module
{
    public Setting<Boolean> autoClip;
    public Setting<Boolean> limit;
    public Setting<Boolean> antiKick;
    public Setting<Float> speed;
    public Setting<Float> timer;
    public Setting<Integer> increaseTicks;
    public Setting<Integer> factor;
    private final Setting<Mode> mode;
    private final Setting<Phase> phase;
    private final Setting<Type> type;
    private int Field3526;
    private final ConcurrentSet Field3527;
    private final Random Field3528;
    private final ConcurrentHashMap Field3529;
    private int Field3530;
    private int Field3531;
    private int Field3532;
    private boolean Field3533;
    private boolean Field3534;
    
    public PacketFly2() {
        super("PacketFly2", "PacketFly2", Module.Category.MOVEMENT);
        this.autoClip = (Setting<Boolean>)this.register(new Setting("AutoClip", (T)false));
        this.limit = (Setting<Boolean>)this.register(new Setting("Limit", (T)true));
        this.antiKick = (Setting<Boolean>)this.register(new Setting("AntiKick", (T)true));
        this.speed = (Setting<Float>)this.register(new Setting("Speed", (T)1.0f, (T)0.0f, (T)3.0f));
        this.timer = (Setting<Float>)this.register(new Setting("Timer", (T)1.0f, (T)0.0f, (T)2.0f));
        this.increaseTicks = (Setting<Integer>)this.register(new Setting("IncreaseTicks", (T)20, (T)1, (T)20));
        this.factor = (Setting<Integer>)this.register(new Setting("Factor", (T)1, (T)1, (T)10));
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.Fast));
        this.phase = (Setting<Phase>)this.register(new Setting("Phase", (T)Phase.Full));
        this.type = (Setting<Type>)this.register(new Setting("Type", (T)Type.Preserve));
        this.Field3526 = -1;
        this.Field3527 = new ConcurrentSet();
        this.Field3528 = new Random();
        this.Field3529 = new ConcurrentHashMap();
        this.Field3530 = 0;
        this.Field3531 = 0;
        this.Field3532 = 0;
        this.Field3533 = false;
        this.Field3534 = false;
    }
    
    private static boolean Method4295(final Object o) {
        return System.currentTimeMillis() - ((Map.Entry)o).getValue().Method1915() > TimeUnit.SECONDS.toMillis(30L);
    }
    
    public static boolean Method103() {
        return Util.mc.player != null && (Util.mc.player.movementInput.moveForward != 0.0f || Util.mc.player.movementInput.moveStrafe != 0.0f);
    }
    
    public static double[] Method3180(final double d) {
        double d2 = Util.mc.player.movementInput.moveForward;
        double d3 = Util.mc.player.movementInput.moveStrafe;
        float f = Util.mc.player.rotationYaw;
        final double[] dArray = new double[2];
        if (d2 == 0.0 && d3 == 0.0) {
            dArray[1] = (dArray[0] = 0.0);
        }
        else {
            if (d2 != 0.0) {
                if (d3 > 0.0) {
                    f += ((d2 > 0.0) ? -45 : 45);
                }
                else if (d3 < 0.0) {
                    f += ((d2 > 0.0) ? 45 : -45);
                }
                d3 = 0.0;
                if (d2 > 0.0) {
                    d2 = 1.0;
                }
                else if (d2 < 0.0) {
                    d2 = -1.0;
                }
            }
            dArray[0] = d2 * d * Math.cos(Math.toRadians(f + 90.0f)) + d3 * d * Math.sin(Math.toRadians(f + 90.0f));
            dArray[1] = d2 * d * Math.sin(Math.toRadians(f + 90.0f)) - d3 * d * Math.cos(Math.toRadians(f + 90.0f));
        }
        return dArray;
    }
    
    public void onEnable() {
        this.Field3526 = -1;
        this.Field3531 = 0;
        this.Field3532 = 0;
        if (fullNullCheck() && Util.mc.player != null) {
            this.Method4286();
        }
        if (this.autoClip.getValue() && this.Field3534) {
            Util.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Util.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
    
    public void onDisable() {
        Thunderhack.TICK_TIMER = 1.0f;
    }
    
    public void Method4286() {
        this.Field3530 = 0;
        this.Field3526 = 0;
        this.Field3527.clear();
        this.Field3529.clear();
    }
    
    public boolean Method4289(final int n) {
        ++this.Field3530;
        if (this.Field3530 >= n) {
            this.Field3530 = 0;
            return true;
        }
        return false;
    }
    
    public void Method4290(final CPacketPlayer cPacketPlayer) {
        this.Field3527.add((Object)cPacketPlayer);
        Util.mc.player.connection.sendPacket((Packet)cPacketPlayer);
    }
    
    private int Method4291() {
        if (Util.mc.isSingleplayer()) {
            return 2000;
        }
        final int n = this.Field3528.nextInt(29000000);
        if (this.Field3528.nextBoolean()) {
            return n;
        }
        return -n;
    }
    
    public Vec3d Method4292(final Vec3d vec3d, final Vec3d vec3d2) {
        Vec3d vec3d3 = vec3d.add(vec3d2);
        switch (this.type.getValue()) {
            case Preserve: {
                vec3d3 = vec3d3.add((double)this.Method4291(), 0.0, (double)this.Method4291());
                break;
            }
            case Up: {
                vec3d3 = vec3d3.add(0.0, 1337.0, 0.0);
                break;
            }
            case Down: {
                vec3d3 = vec3d3.add(0.0, -1337.0, 0.0);
                break;
            }
            case Bounds: {
                vec3d3 = new Vec3d(vec3d3.x, (Util.mc.player.posY <= 10.0) ? 255.0 : 1.0, vec3d3.z);
                break;
            }
        }
        return vec3d3;
    }
    
    public void Method4293(final Double d, final Double d2, final Double d3, final Boolean bl) {
        final Vec3d vec3d = new Vec3d((double)d, (double)d2, (double)d3);
        final Vec3d vec3d2 = Util.mc.player.getPositionVector().add(vec3d);
        final Vec3d vec3d3 = this.Method4292(vec3d, vec3d2);
        this.Method4290((CPacketPlayer)new CPacketPlayer.Position(vec3d2.x, vec3d2.y, vec3d2.z, Util.mc.player.onGround));
        this.Method4290((CPacketPlayer)new CPacketPlayer.Position(vec3d3.x, vec3d3.y, vec3d3.z, Util.mc.player.onGround));
        if (bl) {
            Util.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(++this.Field3526));
            this.Field3529.put(this.Field3526, new Class443(vec3d2.x, vec3d2.y, vec3d2.z, System.currentTimeMillis()));
        }
    }
    
    private boolean Method4294() {
        return !Util.mc.world.getCollisionBoxes((Entity)Util.mc.player, Util.mc.player.getEntityBoundingBox().expand(-0.0625, -0.0625, -0.0625)).isEmpty();
    }
    
    @SubscribeEvent
    public void Method4282(final PacketEvent.Receive eventNetworkPrePacketEvent) {
        if (fullNullCheck()) {
            return;
        }
        if (Util.mc.player != null && eventNetworkPrePacketEvent.getPacket() instanceof SPacketPlayerPosLook) {
            final SPacketPlayerPosLook sPacketPlayerPosLook = (SPacketPlayerPosLook)eventNetworkPrePacketEvent.getPacket();
            final Class443 class443 = this.Field3529.remove(((ISPacketPlayerPosLook)sPacketPlayerPosLook).getTeleportId());
            if (Util.mc.player.isEntityAlive() && Util.mc.world.isBlockLoaded(new BlockPos(Util.mc.player.posX, Util.mc.player.posY, Util.mc.player.posZ), false) && !(Util.mc.currentScreen instanceof GuiDownloadTerrain) && this.mode.getValue() != Mode.Rubber && class443 != null && Class443.Method1920(class443) == sPacketPlayerPosLook.getX() && Class443.Method1921(class443) == sPacketPlayerPosLook.getY() && Class443.Method1922(class443) == sPacketPlayerPosLook.getZ()) {
                eventNetworkPrePacketEvent.setCanceled(true);
                return;
            }
            ((ISPacketPlayerPosLook)sPacketPlayerPosLook).setYaw(Util.mc.player.rotationYaw);
            ((ISPacketPlayerPosLook)sPacketPlayerPosLook).setPitch(Util.mc.player.rotationPitch);
            this.Field3526 = sPacketPlayerPosLook.getTeleportId();
        }
    }
    
    @SubscribeEvent
    public void Method4281(final PacketEvent.Send eventNetworkPostPacketEvent) {
        if (eventNetworkPostPacketEvent.getPacket() instanceof CPacketPlayer) {
            if (this.Field3527.contains((Object)eventNetworkPostPacketEvent.getPacket())) {
                this.Field3527.remove((Object)eventNetworkPostPacketEvent.getPacket());
                return;
            }
            eventNetworkPostPacketEvent.setCanceled(true);
        }
    }
    
    public void onUpdate() {
        this.Field3529.entrySet().removeIf(PacketFly2::Method4295);
    }
    
    @SubscribeEvent
    public void Method4279(final EventMove eventPlayerMove) {
        if (!eventPlayerMove.isCanceled()) {
            if (this.mode.getValue() != Mode.Rubber && this.Field3526 == 0) {
                return;
            }
            eventPlayerMove.setCanceled(true);
            eventPlayerMove.set_x(Util.mc.player.motionX);
            eventPlayerMove.set_y(Util.mc.player.motionY);
            eventPlayerMove.set_z(Util.mc.player.motionZ);
            if (this.phase.getValue() != Phase.Off && (this.phase.getValue() == Phase.Semi || this.Method4294())) {
                Util.mc.player.noClip = true;
            }
        }
    }
    
    @SubscribeEvent
    public void Method4278(final EventSync eventPlayerUpdateWalking) {
        if (this.timer.getValue() != 1.0) {
            Thunderhack.TICK_TIMER = this.timer.getValue();
        }
        Util.mc.player.setVelocity(0.0, 0.0, 0.0);
        if (this.mode.getValue() != Mode.Rubber && this.Field3526 == 0) {
            if (this.Method4289(4)) {
                this.Method4293(0.0, 0.0, 0.0, false);
            }
            return;
        }
        final boolean bl = this.Method4294();
        double d = 0.0;
        d = ((Util.mc.player.movementInput.jump && (bl || !Method103())) ? ((this.antiKick.getValue() && !bl) ? (this.Method4289((this.mode.getValue() == Mode.Rubber) ? 10 : 20) ? -0.032 : 0.062) : 0.062) : (Util.mc.player.movementInput.sneak ? -0.062 : (bl ? 0.0 : (this.Method4289(4) ? (this.antiKick.getValue() ? -0.04 : 0.0) : 0.0))));
        if (this.phase.getValue() == Phase.Full && bl && Method103() && d != 0.0) {
            d = (Util.mc.player.movementInput.jump ? (d /= 2.5) : (d /= 1.5));
        }
        if (Util.mc.player.movementInput.jump && this.autoClip.getValue()) {
            Util.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Util.mc.player, this.Field3534 ? CPacketEntityAction.Action.START_SNEAKING : CPacketEntityAction.Action.STOP_SNEAKING));
            this.Field3534 = !this.Field3534;
        }
        final double[] dArray = Method3180((this.phase.getValue() == Phase.Full && bl) ? 0.034444444444444444 : (this.speed.getValue() * 0.26));
        int n = 1;
        if (this.mode.getValue() == Mode.Factor && Util.mc.player.ticksExisted % this.increaseTicks.getValue() == 0) {
            n = this.factor.getValue();
        }
        for (int i = 1; i <= n; ++i) {
            if (this.mode.getValue() == Mode.Limit) {
                if (Util.mc.player.ticksExisted % 2 == 0) {
                    if (this.Field3533 && d >= 0.0) {
                        this.Field3533 = false;
                        d = -0.032;
                    }
                    Util.mc.player.motionX = dArray[0] * i;
                    Util.mc.player.motionZ = dArray[1] * i;
                    Util.mc.player.motionY = d * i;
                    this.Method4293(Util.mc.player.motionX, Util.mc.player.motionY, Util.mc.player.motionZ, !this.limit.getValue());
                }
                else if (d < 0.0) {
                    this.Field3533 = true;
                }
            }
            else {
                Util.mc.player.motionX = dArray[0] * i;
                Util.mc.player.motionZ = dArray[1] * i;
                Util.mc.player.motionY = d * i;
                this.Method4293(Util.mc.player.motionX, Util.mc.player.motionY, Util.mc.player.motionZ, this.mode.getValue() != Mode.Rubber);
            }
        }
    }
    
    public enum Mode
    {
        Fast, 
        Factor, 
        Rubber, 
        Limit;
    }
    
    public enum Phase
    {
        Full, 
        Off, 
        Semi;
    }
    
    public enum Type
    {
        Preserve, 
        Up, 
        Down, 
        Bounds;
    }
    
    public static class Class443
    {
        private final double Field1501;
        private final double Field1502;
        private final double Field1503;
        private final long Field1504;
        
        public Class443(final double d, final double d2, final double d3, final long l) {
            this.Field1501 = d;
            this.Field1502 = d2;
            this.Field1503 = d3;
            this.Field1504 = l;
        }
        
        static double Method1920(final Class443 class443) {
            return class443.Field1501;
        }
        
        static double Method1921(final Class443 class443) {
            return class443.Field1502;
        }
        
        static double Method1922(final Class443 class443) {
            return class443.Field1503;
        }
        
        public long Method1915() {
            return this.Field1504;
        }
    }
}
