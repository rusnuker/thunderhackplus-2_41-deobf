//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class MSTSpeed extends Module
{
    static int ticks;
    static int maxticks2;
    public Setting<Integer> maxticks;
    public Setting<Float> speed;
    public Setting<Float> airspeed;
    public Setting<Boolean> onlyGround;
    
    public MSTSpeed() {
        super("DMGSpeed", "Matrix moment", Module.Category.MOVEMENT);
        this.maxticks = (Setting<Integer>)this.register(new Setting("Ticks", (T)20, (T)1, (T)100));
        this.speed = (Setting<Float>)this.register(new Setting("Speed", (T)0.7f, (T)0.1f, (T)2.0f));
        this.airspeed = (Setting<Float>)this.register(new Setting("AirSpeed", (T)0.7f, (T)0.1f, (T)2.0f));
        this.onlyGround = (Setting<Boolean>)this.register(new Setting("onlyGround", (T)true));
    }
    
    public static double getProgress() {
        return MSTSpeed.ticks / (double)MSTSpeed.maxticks2;
    }
    
    public void onUpdate() {
        if (MSTSpeed.ticks > 0) {
            --MSTSpeed.ticks;
        }
        if (MSTSpeed.mc.player.moveForward == 0.0f && MSTSpeed.mc.player.moveStrafing == 0.0f) {
            return;
        }
        MSTSpeed.maxticks2 = this.maxticks.getValue();
        if (MSTSpeed.ticks > 0) {
            MSTSpeed.mc.player.setSprinting(true);
            if (MSTSpeed.mc.player.onGround) {
                MSTSpeed.mc.player.motionX = -MathHelper.sin(this.get_rotation_yaw()) * (double)this.speed.getValue();
                MSTSpeed.mc.player.motionZ = MathHelper.cos(this.get_rotation_yaw()) * (double)this.speed.getValue();
            }
            else if (this.onlyGround.getValue() && !MSTSpeed.mc.player.onGround) {
                MSTSpeed.mc.player.motionX = -MathHelper.sin(this.get_rotation_yaw()) * (double)this.airspeed.getValue();
                MSTSpeed.mc.player.motionZ = MathHelper.cos(this.get_rotation_yaw()) * (double)this.airspeed.getValue();
            }
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)event.getPacket()).getEntityID() == MSTSpeed.mc.player.getEntityId()) {
            MSTSpeed.ticks = this.maxticks.getValue();
        }
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            MSTSpeed.ticks = 0;
        }
    }
    
    private float get_rotation_yaw() {
        float rotation_yaw = MSTSpeed.mc.player.rotationYaw;
        if (MSTSpeed.mc.player.moveForward < 0.0f) {
            rotation_yaw += 180.0f;
        }
        float n = 1.0f;
        if (MSTSpeed.mc.player.moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (MSTSpeed.mc.player.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (MSTSpeed.mc.player.moveStrafing > 0.0f) {
            rotation_yaw -= 90.0f * n;
        }
        if (MSTSpeed.mc.player.moveStrafing < 0.0f) {
            rotation_yaw += 90.0f * n;
        }
        return rotation_yaw * 0.017453292f;
    }
    
    static {
        MSTSpeed.ticks = 0;
        MSTSpeed.maxticks2 = 1;
    }
}
