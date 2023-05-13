//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import com.mrzak34.thunderhack.events.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class MovementUtil implements Util
{
    public static boolean isMoving() {
        return MovementUtil.mc.player.moveForward != 0.0 || MovementUtil.mc.player.moveStrafing != 0.0;
    }
    
    public static void strafe(final EventMove event, final double speed) {
        if (isMoving()) {
            final double[] strafe = strafe(speed);
            event.set_x(strafe[0]);
            event.set_z(strafe[1]);
        }
        else {
            event.set_x(0.0);
            event.set_z(0.0);
        }
    }
    
    public static double getSpeed() {
        return Math.hypot(MovementUtil.mc.player.motionX, MovementUtil.mc.player.motionZ);
    }
    
    public static double[] strafe(final double speed) {
        return strafe((Entity)MovementUtil.mc.player, speed);
    }
    
    public static double[] strafe(final Entity entity, final double speed) {
        return strafe(entity, MovementUtil.mc.player.movementInput, speed);
    }
    
    public static double[] strafe(final Entity entity, final MovementInput movementInput, final double speed) {
        float moveForward = movementInput.moveForward;
        float moveStrafe = movementInput.moveStrafe;
        float rotationYaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * MovementUtil.mc.getRenderPartialTicks();
        if (moveForward != 0.0f) {
            if (moveStrafe > 0.0f) {
                rotationYaw += ((moveForward > 0.0f) ? -45 : 45);
            }
            else if (moveStrafe < 0.0f) {
                rotationYaw += ((moveForward > 0.0f) ? 45 : -45);
            }
            moveStrafe = 0.0f;
            if (moveForward > 0.0f) {
                moveForward = 1.0f;
            }
            else if (moveForward < 0.0f) {
                moveForward = -1.0f;
            }
        }
        final double posX = moveForward * speed * -Math.sin(Math.toRadians(rotationYaw)) + moveStrafe * speed * Math.cos(Math.toRadians(rotationYaw));
        final double posZ = moveForward * speed * Math.cos(Math.toRadians(rotationYaw)) - moveStrafe * speed * -Math.sin(Math.toRadians(rotationYaw));
        return new double[] { posX, posZ };
    }
    
    public static double[] forward(final double d) {
        float f = Minecraft.getMinecraft().player.movementInput.moveForward;
        float f2 = Minecraft.getMinecraft().player.movementInput.moveStrafe;
        float f3 = Minecraft.getMinecraft().player.prevRotationYaw + (Minecraft.getMinecraft().player.rotationYaw - Minecraft.getMinecraft().player.prevRotationYaw) * Minecraft.getMinecraft().getRenderPartialTicks();
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
    
    public static boolean isMoving(final EntityLivingBase entityLivingBase) {
        return entityLivingBase.moveForward != 0.0f || entityLivingBase.moveStrafing != 0.0f;
    }
    
    public static void setMotion(final double speed) {
        double forward = MovementUtil.mc.player.movementInput.moveForward;
        double strafe = MovementUtil.mc.player.movementInput.moveStrafe;
        float yaw = MovementUtil.mc.player.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            MovementUtil.mc.player.motionX = 0.0;
            MovementUtil.mc.player.motionZ = 0.0;
        }
        else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                }
                else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                }
                else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            final double sin = MathHelper.sin((float)Math.toRadians(yaw + 90.0f));
            final double cos = MathHelper.cos((float)Math.toRadians(yaw + 90.0f));
            MovementUtil.mc.player.motionX = forward * speed * cos + strafe * speed * sin;
            MovementUtil.mc.player.motionZ = forward * speed * sin - strafe * speed * cos;
        }
    }
}
