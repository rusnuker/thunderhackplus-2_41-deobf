//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;

public class DMGFly extends Module
{
    public static long lastVelocityTime;
    public static double velocityXZ;
    public static double velocityY;
    
    public DMGFly() {
        super("DMGFly", "DMGFly", Module.Category.MOVEMENT);
    }
    
    public static double[] getSpeed(final double speed) {
        float yaw = DMGFly.mc.player.rotationYaw;
        float forward = DMGFly.mc.player.movementInput.moveForward;
        float strafe = DMGFly.mc.player.movementInput.moveStrafe;
        if (forward != 0.0f) {
            if (strafe > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (strafe < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            strafe = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        return new double[] { forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)), forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)), yaw };
    }
    
    public static double getProgress() {
        return (System.currentTimeMillis() - DMGFly.lastVelocityTime > 1350L) ? 0.0 : (1.0 - (System.currentTimeMillis() - DMGFly.lastVelocityTime) / 1350.0);
    }
    
    @SubscribeEvent
    public void onPyroMove(final EventMove e) {
        if (System.currentTimeMillis() - DMGFly.lastVelocityTime < 1350L) {
            final double speed = Math.hypot(e.get_x(), e.get_z()) + DMGFly.velocityXZ - 0.25;
            final double[] brain = getSpeed(speed);
            e.set_x(brain[0]);
            e.set_z(brain[1]);
            if (DMGFly.velocityY > 0.0) {
                e.set_y(DMGFly.velocityY);
            }
            e.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final EventSync e) {
        if (System.currentTimeMillis() - DMGFly.lastVelocityTime < 1350L) {
            DMGFly.mc.player.setSprinting(!DMGFly.mc.player.isSprinting());
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityVelocity) {
            final SPacketEntityVelocity packet = (SPacketEntityVelocity)event.getPacket();
            if (packet.getEntityID() == DMGFly.mc.player.getEntityId() && System.currentTimeMillis() - DMGFly.lastVelocityTime > 1350L) {
                final double vX = Math.abs(packet.getMotionX() / 8000.0);
                final double vY = packet.getMotionY() / 8000.0;
                final double vZ = Math.abs(packet.getMotionZ() / 8000.0);
                if (vX + vZ > 0.3) {
                    DMGFly.velocityXZ = vX + vZ;
                    DMGFly.lastVelocityTime = System.currentTimeMillis();
                    DMGFly.velocityY = vY;
                }
                else {
                    DMGFly.velocityXZ = 0.0;
                    DMGFly.velocityY = 0.0;
                }
            }
        }
    }
}
