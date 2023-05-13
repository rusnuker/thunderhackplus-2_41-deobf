//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.manager.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.util.math.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

public class RotationSmoother
{
    private final RotationManager manager;
    private int rotationTicks;
    private boolean rotating;
    
    public RotationSmoother(final RotationManager manager) {
        this.manager = manager;
    }
    
    public static float[] getRotations(final double x, final double y, final double z, final double fromX, final double fromY, final double fromZ, final float fromHeight) {
        final double xDiff = x - fromX;
        final double yDiff = y - (fromY + fromHeight);
        final double zDiff = z - fromZ;
        final double dist = MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        final float prevYaw = Thunderhack.rotationManager.getServerYaw();
        float diff = yaw - prevYaw;
        if (diff < -180.0f || diff > 180.0f) {
            final float round = (float)Math.round(Math.abs(diff / 360.0f));
            diff = ((diff < 0.0f) ? (diff + 360.0f * round) : (diff - 360.0f * round));
        }
        return new float[] { prevYaw + diff, pitch };
    }
    
    public static float[] faceSmoothly(final double curYaw, final double curPitch, final double intendedYaw, final double intendedPitch, final double yawSpeed, final double pitchSpeed) {
        final float yaw = PhobosRotationUtil.updateRotation((float)curYaw, (float)intendedYaw, (float)yawSpeed);
        final float pitch = PhobosRotationUtil.updateRotation((float)curPitch, (float)intendedPitch, (float)pitchSpeed);
        return new float[] { yaw, pitch };
    }
    
    public static double angle(final float[] rotation1, final float[] rotation2) {
        final Vec3d r1Vec = PhobosRotationUtil.getVec3d(rotation1[0], rotation1[1]);
        final Vec3d r2Vec = PhobosRotationUtil.getVec3d(rotation2[0], rotation2[1]);
        return MathUtil.angle(r1Vec, r2Vec);
    }
    
    public float[] getRotations(final Entity from, final Entity entity, final double height, final double maxAngle) {
        return this.getRotations(entity, from.posX, from.posY, from.posZ, from.getEyeHeight(), height, maxAngle);
    }
    
    public float[] getRotations(final Entity entity, final double fromX, final double fromY, final double fromZ, final float eyeHeight, final double height, final double maxAngle) {
        final float[] rotations = getRotations(entity.posX, entity.posY + entity.getEyeHeight() * height, entity.posZ, fromX, fromY, fromZ, eyeHeight);
        return this.smoothen(rotations, maxAngle);
    }
    
    public float[] smoothen(final float[] rotations, final double maxAngle) {
        final float[] server = { this.manager.getServerYaw(), this.manager.getServerPitch() };
        return this.smoothen(server, rotations, maxAngle);
    }
    
    public float[] smoothen(final float[] server, final float[] rotations, final double maxAngle) {
        if (maxAngle >= 180.0 || maxAngle <= 0.0 || angle(server, rotations) <= maxAngle) {
            this.rotating = false;
            return rotations;
        }
        this.rotationTicks = 0;
        this.rotating = true;
        return faceSmoothly(server[0], server[1], rotations[0], rotations[1], maxAngle, maxAngle);
    }
    
    public void incrementRotationTicks() {
        ++this.rotationTicks;
    }
    
    public int getRotationTicks() {
        return this.rotationTicks;
    }
    
    public boolean isRotating() {
        return this.rotating;
    }
}
