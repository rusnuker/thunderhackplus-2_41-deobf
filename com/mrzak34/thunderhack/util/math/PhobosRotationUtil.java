//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.math;

import net.minecraft.util.math.*;

public class PhobosRotationUtil
{
    public static Vec3d getVec3d(final float yaw, final float pitch) {
        final float vx = -MathHelper.sin(MathUtil.rad(yaw)) * MathHelper.cos(MathUtil.rad(pitch));
        final float vz = MathHelper.cos(MathUtil.rad(yaw)) * MathHelper.cos(MathUtil.rad(pitch));
        final float vy = -MathHelper.sin(MathUtil.rad(pitch));
        return new Vec3d((double)vx, (double)vy, (double)vz);
    }
    
    public static double angle(final float[] rotation1, final float[] rotation2) {
        final Vec3d r1Vec = getVec3d(rotation1[0], rotation1[1]);
        final Vec3d r2Vec = getVec3d(rotation2[0], rotation2[1]);
        return MathUtil.angle(r1Vec, r2Vec);
    }
    
    public static float updateRotation(final float current, final float intended, final float factor) {
        float updated = MathHelper.wrapDegrees(intended - current);
        if (updated > factor) {
            updated = factor;
        }
        if (updated < -factor) {
            updated = -factor;
        }
        return current + updated;
    }
}
