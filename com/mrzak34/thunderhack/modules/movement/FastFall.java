//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;

public class FastFall extends Module
{
    private final Timer rubberbandTimer;
    private final Timer strictTimer;
    public Setting<Float> speed;
    public Setting<Float> shiftTicks;
    public Setting<Float> height;
    public Setting<Boolean> webs;
    private final Setting<Mode> mode;
    private boolean previousOnGround;
    private int ticks;
    private boolean stop;
    
    public FastFall() {
        super("FastFall", "FastFall", Module.Category.MOVEMENT);
        this.rubberbandTimer = new Timer();
        this.strictTimer = new Timer();
        this.speed = (Setting<Float>)this.register(new Setting("Speed", (T)1.0f, (T)3.0f, (T)5.0f));
        this.shiftTicks = (Setting<Float>)this.register(new Setting("Height", (T)2.0f, (T)1.0f, (T)2.5f));
        this.height = (Setting<Float>)this.register(new Setting("Height", (T)0.0f, (T)2.0f, (T)10.0f));
        this.webs = (Setting<Boolean>)this.register(new Setting("Webs", (T)false));
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.MOTION));
    }
    
    public void onTick() {
        this.previousOnGround = FastFall.mc.player.onGround;
    }
    
    public void onUpdate() {
        if (Jesus.isInLiquid() || FastFall.mc.player.isOverWater() || FastFall.mc.player.capabilities.isFlying || FastFall.mc.player.isElytraFlying() || FastFall.mc.player.isOnLadder()) {
            return;
        }
        if (((IEntity)FastFall.mc.player).isInWeb() && !this.webs.getValue()) {
            return;
        }
        if (FastFall.mc.gameSettings.keyBindJump.isKeyDown() || FastFall.mc.gameSettings.keyBindSneak.isKeyDown()) {
            return;
        }
        if (!this.rubberbandTimer.passedMs(1000L)) {
            return;
        }
        if (FastFall.mc.player.onGround && this.mode.getValue().equals(Mode.MOTION)) {
            for (double fallHeight = 0.0; fallHeight < this.height.getValue() + 0.5; fallHeight += 0.01) {
                if (!FastFall.mc.world.getCollisionBoxes((Entity)FastFall.mc.player, FastFall.mc.player.getEntityBoundingBox().offset(0.0, -fallHeight, 0.0)).isEmpty()) {
                    FastFall.mc.player.motionY = -this.speed.getValue();
                    break;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onPostPlayerUpdate(final PostPlayerUpdateEvent event) {
        if (Jesus.isInLiquid() || FastFall.mc.player.isOverWater() || FastFall.mc.player.capabilities.isFlying || FastFall.mc.player.isElytraFlying() || FastFall.mc.player.isOnLadder()) {
            return;
        }
        if (((IEntity)FastFall.mc.player).isInWeb() && !this.webs.getValue()) {
            return;
        }
        if (FastFall.mc.gameSettings.keyBindJump.isKeyDown()) {
            return;
        }
        if (!this.rubberbandTimer.passedMs(1000L)) {
            return;
        }
        if (this.mode.getValue().equals(Mode.PACKET)) {
            event.setCanceled(true);
            if (FastFall.mc.player.motionY < 0.0 && this.previousOnGround && !FastFall.mc.player.onGround) {
                for (double fallHeight = 0.0; fallHeight < this.height.getValue() + 0.5; fallHeight += 0.01) {
                    if (!FastFall.mc.world.getCollisionBoxes((Entity)FastFall.mc.player, FastFall.mc.player.getEntityBoundingBox().offset(0.0, -fallHeight, 0.0)).isEmpty() && this.strictTimer.passedMs(1000L)) {
                        FastFall.mc.player.motionX = 0.0;
                        FastFall.mc.player.motionZ = 0.0;
                        event.setIterations(this.shiftTicks.getValue().intValue());
                        this.stop = true;
                        this.ticks = 0;
                        this.strictTimer.reset();
                        break;
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onMotion(final EventMove event) {
        if (this.mode.getValue().equals(Mode.PACKET) && this.stop) {
            event.setCanceled(true);
            event.setX(0.0);
            event.setZ(0.0);
            FastFall.mc.player.motionX = 0.0;
            FastFall.mc.player.motionZ = 0.0;
            ++this.ticks;
            if (this.ticks > this.shiftTicks.getValue()) {
                this.stop = false;
                this.ticks = 0;
            }
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (fullNullCheck()) {
            return;
        }
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            this.rubberbandTimer.reset();
        }
    }
    
    public enum Mode
    {
        MOTION, 
        PACKET;
    }
}
