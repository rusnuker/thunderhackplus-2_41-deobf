//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.math;

import com.mrzak34.thunderhack.util.*;
import java.math.*;
import net.minecraft.util.math.*;

public class MathUtil implements Util
{
    public static double random(final double min, final double max) {
        return Math.random() * (max - min) + min;
    }
    
    public static float random(final float min, final float max) {
        return (float)(Math.random() * (max - min) + min);
    }
    
    public static float round2(final double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
    
    public static double angle(final Vec3d vec3d, final Vec3d other) {
        final double lengthSq = vec3d.length() * other.length();
        if (lengthSq < 1.0E-4) {
            return 0.0;
        }
        final double dot = vec3d.dotProduct(other);
        final double arg = dot / lengthSq;
        if (arg > 1.0) {
            return 0.0;
        }
        if (arg < -1.0) {
            return 180.0;
        }
        return Math.acos(arg) * 180.0 / 3.141592653589793;
    }
    
    public static Vec3d fromTo(final Vec3d from, final double x, final double y, final double z) {
        return fromTo(from.x, from.y, from.z, x, y, z);
    }
    
    public static float lerp(final float f, final float st, final float en) {
        return st + f * (en - st);
    }
    
    public static Vec3d fromTo(final double x, final double y, final double z, final double x2, final double y2, final double z2) {
        return new Vec3d(x2 - x, y2 - y, z2 - z);
    }
    
    public static float rad(final float angle) {
        return (float)(angle * 3.141592653589793 / 180.0);
    }
    
    public static int clamp(final int num, final int min, final int max) {
        return (num < min) ? min : Math.min(num, max);
    }
    
    public static float clamp(final float num, final float min, final float max) {
        return (num < min) ? min : Math.min(num, max);
    }
    
    public static double clamp(final double num, final double min, final double max) {
        return (num < min) ? min : Math.min(num, max);
    }
    
    public static float sin(final float value) {
        return MathHelper.sin(value);
    }
    
    public static float cos(final float value) {
        return MathHelper.cos(value);
    }
    
    public static float wrapDegrees(final float value) {
        return MathHelper.wrapDegrees(value);
    }
    
    public static double wrapDegrees(final double value) {
        return MathHelper.wrapDegrees(value);
    }
    
    public static double square(final double input) {
        return input * input;
    }
    
    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.FLOOR);
        return bd.doubleValue();
    }
    
    public static Vec3d direction(final float yaw) {
        return new Vec3d(Math.cos(degToRad(yaw + 90.0f)), 0.0, Math.sin(degToRad(yaw + 90.0f)));
    }
    
    public static float round(final float value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
    
    public static double degToRad(final double deg) {
        return deg * 0.01745329238474369;
    }
    
    public static double[] directionSpeed(final double speed) {
        float forward = MathUtil.mc.player.movementInput.moveForward;
        float side = MathUtil.mc.player.movementInput.moveStrafe;
        float yaw = MathUtil.mc.player.prevRotationYaw + (MathUtil.mc.player.rotationYaw - MathUtil.mc.player.prevRotationYaw) * MathUtil.mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        side = clamp(side, -1.0f, 1.0f);
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[] { posX, posZ };
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    public static double roundToClosest(final double num, final double low, final double high) {
        final double d1 = num - low;
        final double d2 = high - num;
        if (d2 > d1) {
            return low;
        }
        return high;
    }
}
