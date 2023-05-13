//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import java.util.*;

public class RayTraceFactory
{
    private static final EnumFacing[] T;
    private static final EnumFacing[] B;
    private static final EnumFacing[] S;
    
    private RayTraceFactory() {
        throw new AssertionError();
    }
    
    public static Ray fullTrace(final Entity from, final IBlockAccess world, final BlockPos pos, final double resolution) {
        Ray dumbRay = null;
        double closest = Double.MAX_VALUE;
        for (final EnumFacing facing : getOptimalFacings(from, pos)) {
            final BlockPos offset = pos.offset(facing);
            final IBlockState state = world.getBlockState(offset);
            if (!state.getMaterial().isReplaceable()) {
                final Ray ray = rayTrace(from, offset, facing.getOpposite(), world, state, resolution);
                if (ray.isLegit()) {
                    return ray;
                }
                final double dist = BlockUtils.getDistanceSq(from, offset);
                if (dumbRay == null || dist < closest) {
                    closest = dist;
                    dumbRay = ray;
                }
            }
        }
        return dumbRay;
    }
    
    public static Ray rayTrace(final Entity from, final BlockPos on, final EnumFacing facing, final IBlockAccess access, final IBlockState state, final double res) {
        final Vec3d start = Burrow.getEyePos(from);
        final AxisAlignedBB bb = state.getBoundingBox(access, on);
        if (res >= 1.0) {
            final float[] r = rots(on, facing, from, access, state);
            final Vec3d look = RotationUtil.getVec3d(r[0], r[1]);
            final double d = Util.mc.playerController.getBlockReachDistance();
            final Vec3d rotations = start.add(look.x * d, look.y * d, look.z * d);
            final RayTraceResult result = RayTracer.trace((World)Util.mc.world, access, start, rotations, false, false, true);
            if (result == null || result.sideHit != facing || !on.equals((Object)result.getBlockPos())) {
                return dumbRay(on, facing, r);
            }
            return new Ray(result, r, on, facing, (Vec3d)null).setLegit(true);
        }
        else {
            final Vec3i dirVec = facing.getDirectionVec();
            final double dirX = (dirVec.getX() < 0) ? bb.minX : (dirVec.getX() * bb.maxX);
            final double dirY = (dirVec.getY() < 0) ? bb.minY : (dirVec.getY() * bb.maxY);
            final double dirZ = (dirVec.getZ() < 0) ? bb.minZ : (dirVec.getZ() * bb.maxZ);
            double minX = on.getX() + dirX + ((dirVec.getX() == 0) ? bb.minX : 0.0);
            double minY = on.getY() + dirY + ((dirVec.getY() == 0) ? bb.minY : 0.0);
            double minZ = on.getZ() + dirZ + ((dirVec.getZ() == 0) ? bb.minZ : 0.0);
            double maxX = on.getX() + dirX + ((dirVec.getX() == 0) ? bb.maxX : 0.0);
            double maxY = on.getY() + dirY + ((dirVec.getY() == 0) ? bb.maxY : 0.0);
            double maxZ = on.getZ() + dirZ + ((dirVec.getZ() == 0) ? bb.maxZ : 0.0);
            final boolean xEq = Double.compare(minX, maxX) == 0;
            final boolean yEq = Double.compare(minY, maxY) == 0;
            final boolean zEq = Double.compare(minZ, maxZ) == 0;
            if (xEq) {
                minX = (maxX = minX - dirVec.getX() * 5.0E-4);
            }
            if (yEq) {
                minY = (maxY = minY - dirVec.getY() * 5.0E-4);
            }
            if (zEq) {
                minZ = (maxZ = minZ - dirVec.getZ() * 5.0E-4);
            }
            final double endX = Math.max(minX, maxX) - (xEq ? 0.0 : 5.0E-4);
            final double endY = Math.max(minY, maxY) - (yEq ? 0.0 : 5.0E-4);
            final double endZ = Math.max(minZ, maxZ) - (zEq ? 0.0 : 5.0E-4);
            if (res <= 0.0) {
                final double staX = Math.min(minX, maxX) + (xEq ? 0.0 : 5.0E-4);
                final double staY = Math.min(minY, maxY) + (yEq ? 0.0 : 5.0E-4);
                final double staZ = Math.min(minZ, maxZ) + (zEq ? 0.0 : 5.0E-4);
                final Set<Vec3d> vectors = new HashSet<Vec3d>();
                vectors.add(new Vec3d(staX, staY, staZ));
                vectors.add(new Vec3d(staX, staY, endZ));
                vectors.add(new Vec3d(staX, endY, staZ));
                vectors.add(new Vec3d(staX, endY, endZ));
                vectors.add(new Vec3d(endX, staY, staZ));
                vectors.add(new Vec3d(endX, staY, endZ));
                vectors.add(new Vec3d(endX, endY, staZ));
                vectors.add(new Vec3d(endX, endY, endZ));
                final double x = (endX - staX) / 2.0 + staX;
                final double y = (endY - staY) / 2.0 + staY;
                final double z = (endZ - staZ) / 2.0 + staZ;
                vectors.add(new Vec3d(x, y, z));
                for (final Vec3d vec : vectors) {
                    final RayTraceResult ray = RayTracer.trace((World)Util.mc.world, access, start, vec, false, false, true);
                    if (ray != null && on.equals((Object)ray.getBlockPos()) && facing == ray.sideHit) {
                        return new Ray(ray, rots(from, vec), on, facing, vec).setLegit(true);
                    }
                }
                return dumbRay(on, facing, rots(on, facing, from, access, state));
            }
            for (double x2 = Math.min(minX, maxX); x2 <= endX; x2 += res) {
                for (double y2 = Math.min(minY, maxY); y2 <= endY; y2 += res) {
                    for (double z2 = Math.min(minZ, maxZ); z2 <= endZ; z2 += res) {
                        final Vec3d vector = new Vec3d(x2, y2, z2);
                        final RayTraceResult ray2 = RayTracer.trace((World)Util.mc.world, access, start, vector, false, false, true);
                        if (ray2 != null && facing == ray2.sideHit && on.equals((Object)ray2.getBlockPos())) {
                            return new Ray(ray2, rots(from, vector), on, facing, vector).setLegit(true);
                        }
                    }
                }
            }
            return dumbRay(on, facing, rots(on, facing, from, access, state));
        }
    }
    
    public static Ray dumbRay(final BlockPos on, final EnumFacing offset, final float[] rotations) {
        return newRay(new RayTraceResult(RayTraceResult.Type.MISS, new Vec3d(0.5, 1.0, 0.5), EnumFacing.UP, BlockPos.ORIGIN), on, offset, rotations);
    }
    
    public static Ray newRay(final RayTraceResult result, final BlockPos on, final EnumFacing offset, final float[] rotations) {
        return new Ray(result, rotations, on, offset, (Vec3d)null);
    }
    
    static float[] rots(final Entity from, final Vec3d vec3d) {
        return RotationUtil.getRotations(vec3d.x, vec3d.y, vec3d.z, from);
    }
    
    private static float[] rots(final BlockPos pos, final EnumFacing facing, final Entity from, final IBlockAccess world, final IBlockState state) {
        return RotationUtil.getRotations(pos, facing, from, world, state);
    }
    
    private static EnumFacing[] getOptimalFacings(final Entity player, final BlockPos pos) {
        if (pos.getY() > player.posY + 2.0) {
            return RayTraceFactory.T;
        }
        if (pos.getY() < player.posY) {
            return RayTraceFactory.B;
        }
        return RayTraceFactory.S;
    }
    
    static {
        T = new EnumFacing[] { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST, EnumFacing.DOWN };
        B = new EnumFacing[] { EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST, EnumFacing.UP };
        S = new EnumFacing[] { EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.UP, EnumFacing.DOWN };
    }
}
