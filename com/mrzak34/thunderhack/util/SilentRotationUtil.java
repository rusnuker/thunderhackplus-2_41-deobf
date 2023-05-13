//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class SilentRotationUtil
{
    public static void lookAtVector(final Vec3d vec) {
        final float[] angle = calcAngle(Util.mc.player.getPositionEyes(Util.mc.getRenderPartialTicks()), vec);
        setPlayerRotations(angle[0], angle[1]);
        Util.mc.player.renderYawOffset = angle[0];
        Util.mc.player.rotationYawHead = angle[0];
    }
    
    public static void lookAtVec3d(final Vec3d vec3d) {
        final float[] angle = calculateAngle(Util.mc.player.getPositionEyes(Util.mc.getRenderPartialTicks()), new Vec3d(vec3d.x, vec3d.y, vec3d.z));
        Util.mc.player.rotationPitch = angle[1];
        Util.mc.player.rotationYaw = angle[0];
    }
    
    public static void lookAtXYZ(final double x, final double y, final double z) {
        final Vec3d vec3d = new Vec3d(x, y, z);
        lookAtVec3d(vec3d);
    }
    
    public static void lookAtEntity(final Entity entity) {
        final float[] angle = calcAngle(Util.mc.player.getPositionEyes(Util.mc.getRenderPartialTicks()), entity.getPositionEyes(Util.mc.getRenderPartialTicks()));
        lookAtAngles(angle[0], angle[1]);
    }
    
    public static void lookAtAngles(final float yaw, final float pitch) {
        setPlayerRotations(yaw, pitch);
        Util.mc.player.rotationYawHead = yaw;
    }
    
    public static void lookAtBlock(final BlockPos blockPos) {
        final float[] angle = calcAngle(Util.mc.player.getPositionEyes(Util.mc.getRenderPartialTicks()), new Vec3d((Vec3i)blockPos));
        setPlayerRotations(angle[0], angle[1]);
        Util.mc.player.renderYawOffset = angle[0];
        Util.mc.player.rotationYawHead = angle[0];
    }
    
    public static void setPlayerRotations(final float yaw, final float pitch) {
        Util.mc.player.rotationYaw = yaw;
        Util.mc.player.rotationYawHead = yaw;
        Util.mc.player.rotationPitch = pitch;
    }
    
    public static float[] calcAngle(final Vec3d to) {
        if (to == null) {
            return null;
        }
        final double difX = to.x - Util.mc.player.getPositionEyes(1.0f).x;
        final double difY = (to.y - Util.mc.player.getPositionEyes(1.0f).y) * -1.0;
        final double difZ = to.z - Util.mc.player.getPositionEyes(1.0f).z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    public static float[] calcAngle(final BlockPos to) {
        if (to == null) {
            return null;
        }
        final double difX = to.getX() - Util.mc.player.getPositionEyes(1.0f).x;
        final double difY = (to.getY() - Util.mc.player.getPositionEyes(1.0f).y) * -1.0;
        final double difZ = to.getZ() - Util.mc.player.getPositionEyes(1.0f).z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    public static float[] calculateAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        final float yD = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0);
        float pD = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist)));
        if (pD > 90.0f) {
            pD = 90.0f;
        }
        else if (pD < -90.0f) {
            pD = -90.0f;
        }
        return new float[] { yD, pD };
    }
}
