//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.util.math.*;

public class Visible
{
    public static final Visible INSTANCE;
    private final NcpInteractTrace rayTracing;
    private final RayChecker checker;
    
    public Visible() {
        this.rayTracing = new NcpInteractTrace();
        this.checker = new RayChecker();
        this.rayTracing.setMaxSteps(60);
    }
    
    public static boolean isSameBlock(final int x1, final int y1, final int z1, final double x2, final double y2, final double z2) {
        return x1 == floor(x2) && z1 == floor(z2) && y1 == floor(y2);
    }
    
    public static int floor(final double num) {
        final int floor = (int)num;
        return (floor == num) ? floor : (floor - (int)(Double.doubleToRawLongBits(num) >>> 63));
    }
    
    public boolean check(final BlockPos pos) {
        return this.check(pos, 10);
    }
    
    public boolean check(final BlockPos pos, final int ticks) {
        this.checker.ticksToCheck = ticks;
        final Entity entity = (Entity)Util.mc.player;
        return this.check(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch, pos);
    }
    
    public boolean check(final double x, final double y, final double z, final float yaw, final float pitch, final BlockPos pos) {
        final int blockX = pos.getX();
        final int blockY = pos.getY();
        final int blockZ = pos.getZ();
        final double eyeY = y + Util.mc.player.getEyeHeight();
        final boolean collides = !isSameBlock(blockX, blockY, blockZ, x, eyeY, z) && !this.checker.checkFlyingQueue(x, eyeY, z, yaw, pitch, blockX, blockY, blockZ, AutoCrystal.POSITION_HISTORY);
        return collides;
    }
    
    private boolean checkRayTracing(final double eyeX, final double eyeY, final double eyeZ, final double dirX, final double dirY, final double dirZ, final int blockX, final int blockY, final int blockZ) {
        final int eyeBlockX = floor(eyeX);
        final int eyeBlockY = floor(eyeY);
        final int eyeBlockZ = floor(eyeZ);
        final int bdX = blockX - eyeBlockX;
        final int bdY = blockY - eyeBlockY;
        final int bdZ = blockZ - eyeBlockZ;
        final double tMinX = this.getMinTime(eyeX, eyeBlockX, dirX, bdX);
        final double tMinY = this.getMinTime(eyeY, eyeBlockY, dirY, bdY);
        final double tMinZ = this.getMinTime(eyeZ, eyeBlockZ, dirZ, bdZ);
        final double tMaxX = this.getMaxTime(eyeX, eyeBlockX, dirX, tMinX);
        final double tMaxY = this.getMaxTime(eyeY, eyeBlockY, dirY, tMinY);
        final double tMaxZ = this.getMaxTime(eyeZ, eyeBlockZ, dirZ, tMinZ);
        final double tCollide = Math.max(0.0, Math.max(tMinX, Math.max(tMinY, tMinZ)));
        double collideX = this.toBlock(eyeX + dirX * tCollide, blockX);
        double collideY = this.toBlock(eyeY + dirY * tCollide, blockY);
        double collideZ = this.toBlock(eyeZ + dirZ * tCollide, blockZ);
        if ((tMinX > tMaxY && tMinX > tMaxZ) || (tMinY > tMaxX && tMinY > tMaxZ) || (tMinZ > tMaxX && tMaxZ > tMaxY)) {
            collideX = this.postCorrect(blockX, bdX, collideX);
            collideY = this.postCorrect(blockY, bdY, collideY);
            collideZ = this.postCorrect(blockZ, bdZ, collideZ);
        }
        if (tMinX == tCollide) {
            collideX = (double)Math.round(collideX);
        }
        if (tMinY == tCollide) {
            collideY = (double)Math.round(collideY);
        }
        if (tMinZ == tCollide) {
            collideZ = (double)Math.round(collideZ);
        }
        this.rayTracing.set(eyeX, eyeY, eyeZ, collideX, collideY, collideZ, blockX, blockY, blockZ);
        this.rayTracing.loop();
        final boolean collides = this.rayTracing.collides || this.rayTracing.getStepsDone() > this.rayTracing.getMaxSteps();
        return collides;
    }
    
    private double postCorrect(final int blockC, final int bdC, final double collideC) {
        final int ref = (bdC < 0) ? (blockC + 1) : blockC;
        if (floor(collideC) == ref) {
            return collideC;
        }
        return ref;
    }
    
    private double getMinTime(final double eye, final int eyeBlock, final double dir, final int blockDiff) {
        if (blockDiff == 0) {
            return 0.0;
        }
        final double eyeOffset = Math.abs(eye - eyeBlock);
        return (((dir < 0.0) ? eyeOffset : (1.0 - eyeOffset)) + (Math.abs(blockDiff) - 1)) / Math.abs(dir);
    }
    
    private double getMaxTime(final double eye, final int eyeBlock, final double dir, final double tMin) {
        if (dir == 0.0) {
            return Double.MAX_VALUE;
        }
        if (tMin == 0.0) {
            final double eyeOffset = Math.abs(eye - eyeBlock);
            return ((dir < 0.0) ? eyeOffset : (1.0 - eyeOffset)) / Math.abs(dir);
        }
        return tMin + 1.0 / Math.abs(dir);
    }
    
    private double toBlock(final double coord, final int block) {
        final int blockDiff = block - floor(coord);
        if (blockDiff == 0) {
            return coord;
        }
        return (double)Math.round(coord);
    }
    
    static {
        INSTANCE = new Visible();
    }
    
    private class RayChecker extends PositionHistoryChecker
    {
        protected boolean check(final double x, final double y, final double z, final float yaw, final float pitch, final int blockX, final int blockY, final int blockZ) {
            final Vec3d direction = RotationUtil.getVec3d(yaw, pitch);
            return !Visible.this.checkRayTracing(x, y, z, direction.x, direction.y, direction.z, blockX, blockY, blockZ);
        }
        
        public boolean checkFlyingQueue(final double x, final double y, final double z, final float oldYaw, final float oldPitch, final int blockX, final int blockY, final int blockZ, final PositionHistoryHelper history) {
            return super.checkFlyingQueue(x, y, z, oldYaw, oldPitch, blockX, blockY, blockZ, history);
        }
    }
}
