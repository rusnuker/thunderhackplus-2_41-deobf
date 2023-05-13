//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.*;
import com.mrzak34.thunderhack.util.math.*;

public class RotationUtil implements Util
{
    public static EntityPlayer getRotationPlayer() {
        final EntityPlayer rotationEntity = (EntityPlayer)RotationUtil.mc.player;
        return (EntityPlayer)((rotationEntity == null) ? RotationUtil.mc.player : rotationEntity);
    }
    
    public static float[] getRotations(final BlockPos pos, final EnumFacing facing) {
        return getRotations(pos, facing, (Entity)getRotationPlayer());
    }
    
    public static float[] getRotations(final BlockPos pos, final EnumFacing facing, final Entity from) {
        return getRotations(pos, facing, from, (IBlockAccess)RotationUtil.mc.world, RotationUtil.mc.world.getBlockState(pos));
    }
    
    public static float[] getRotations(final BlockPos pos, final EnumFacing facing, final Entity from, final IBlockAccess world, final IBlockState state) {
        final AxisAlignedBB bb = state.getBoundingBox(world, pos);
        double x = pos.getX() + (bb.minX + bb.maxX) / 2.0;
        double y = pos.getY() + (bb.minY + bb.maxY) / 2.0;
        double z = pos.getZ() + (bb.minZ + bb.maxZ) / 2.0;
        if (facing != null) {
            x += facing.getDirectionVec().getX() * ((bb.minX + bb.maxX) / 2.0);
            y += facing.getDirectionVec().getY() * ((bb.minY + bb.maxY) / 2.0);
            z += facing.getDirectionVec().getZ() * ((bb.minZ + bb.maxZ) / 2.0);
        }
        return getRotations(x, y, z, from);
    }
    
    public static float[] getRotations(final double x, final double y, final double z, final Entity f) {
        return getRotations(x, y, z, f.posX, f.posY, f.posZ, f.getEyeHeight());
    }
    
    public static float[] getRotations(final double x, final double y, final double z, final double fromX, final double fromY, final double fromZ, final float fromHeight) {
        final double xDiff = x - fromX;
        final double yDiff = y - (fromY + fromHeight);
        final double zDiff = z - fromZ;
        final double dist = MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        final float prevYaw = RotationUtil.mc.player.rotationYaw;
        float diff = yaw - prevYaw;
        if (diff < -180.0f || diff > 180.0f) {
            final float round = (float)Math.round(Math.abs(diff / 360.0f));
            diff = ((diff < 0.0f) ? (diff + 360.0f * round) : (diff - 360.0f * round));
        }
        return new float[] { prevYaw + diff, pitch };
    }
    
    public static Vec2f getRotationTo(final Vec3d posTo) {
        final EntityPlayerSP player = RotationUtil.mc.player;
        return (player != null) ? getRotationTo(player.getPositionEyes(1.0f), posTo) : Vec2f.ZERO;
    }
    
    public static Vec2f getRotationTo(final Vec3d posFrom, final Vec3d posTo) {
        return getRotationFromVec(posTo.subtract(posFrom));
    }
    
    public static Vec2f getRotationFromVec(final Vec3d vec) {
        final double lengthXZ = Math.hypot(vec.x, vec.z);
        final double yaw = normalizeAngle(Math.toDegrees(Math.atan2(vec.z, vec.x)) - 90.0);
        final double pitch = normalizeAngle(Math.toDegrees(-Math.atan2(vec.y, lengthXZ)));
        return new Vec2f((float)yaw, (float)pitch);
    }
    
    public static double normalizeAngle(double angle) {
        angle %= 360.0;
        if (angle >= 180.0) {
            angle -= 360.0;
        }
        if (angle < -180.0) {
            angle += 360.0;
        }
        return angle;
    }
    
    public static float[] getNeededRotations(final Entity entityLivingBase) {
        final double d = entityLivingBase.posX - Util.mc.player.posX;
        final double d2 = entityLivingBase.posZ - Util.mc.player.posZ;
        final double d3 = entityLivingBase.posY - (Util.mc.player.getEntityBoundingBox().minY + (Util.mc.player.getEntityBoundingBox().maxY - Minecraft.getMinecraft().player.getEntityBoundingBox().minY));
        final double d4 = MathHelper.sqrt(d * d + d2 * d2);
        final float f = (float)(MathHelper.atan2(d2, d) * 180.0 / 3.141592653589793) - 90.0f;
        final float f2 = (float)(-(MathHelper.atan2(d3, d4) * 180.0 / 3.141592653589793));
        return new float[] { f, f2 };
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
    
    public static double angle(final float[] rotation1, final float[] rotation2) {
        final Vec3d r1Vec = getVec3d(rotation1[0], rotation1[1]);
        final Vec3d r2Vec = getVec3d(rotation2[0], rotation2[1]);
        return angle(r1Vec, r2Vec);
    }
    
    public static Vec3d getVec3d(final float yaw, final float pitch) {
        final float vx = -MathHelper.sin(MathUtil.rad(yaw)) * MathHelper.cos(MathUtil.rad(pitch));
        final float vz = MathHelper.cos(MathUtil.rad(yaw)) * MathHelper.cos(MathUtil.rad(pitch));
        final float vy = -MathHelper.sin(MathUtil.rad(pitch));
        return new Vec3d((double)vx, (double)vy, (double)vz);
    }
    
    public static int getDirection4D() {
        return MathHelper.floor(RotationUtil.mc.player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
    }
    
    public static boolean isInFov(final Entity entity) {
        return entity != null && (RotationUtil.mc.player.getDistanceSq(entity) < 4.0 || isInFov(entity.getPositionVector(), RotationUtil.mc.player.getPositionVector()));
    }
    
    public static boolean isInFov(final Vec3d vec3d, final Vec3d other) {
        if (RotationUtil.mc.player.rotationPitch > 30.0f) {
            if (other.y > RotationUtil.mc.player.posY) {
                return true;
            }
        }
        else if (RotationUtil.mc.player.rotationPitch < -30.0f && other.y < RotationUtil.mc.player.posY) {
            return true;
        }
        final float angle = calcAngleNoY(vec3d, other)[0] - transformYaw();
        if (angle < -270.0f) {
            return true;
        }
        final float fov = RotationUtil.mc.gameSettings.fovSetting / 2.0f;
        return angle < fov + 10.0f && angle > -fov - 10.0f;
    }
    
    public static float[] calcAngleNoY(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difZ = to.z - from.z;
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0) };
    }
    
    public static float transformYaw() {
        float yaw = RotationUtil.mc.player.rotationYaw % 360.0f;
        if (RotationUtil.mc.player.rotationYaw > 0.0f) {
            if (yaw > 180.0f) {
                yaw = -180.0f + (yaw - 180.0f);
            }
        }
        else if (yaw < -180.0f) {
            yaw = 180.0f + (yaw + 180.0f);
        }
        if (yaw < 0.0f) {
            return 180.0f + yaw;
        }
        return -180.0f + yaw;
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
}
