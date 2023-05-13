//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.entity.*;
import com.mrzak34.thunderhack.manager.*;
import com.google.common.base.*;
import com.mrzak34.thunderhack.util.*;
import java.util.stream.*;
import java.util.*;
import net.minecraft.world.*;
import java.util.function.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class RayTracer
{
    private static final Predicate<Entity> PREDICATE;
    
    public static RayTraceResult rayTraceEntities(final World world, final Entity from, final double range, final PositionManager position, final RotationManager rotation, final Predicate<Entity> entityCheck, final Entity... additional) {
        return rayTraceEntities(world, from, range, position.getX(), position.getY(), position.getZ(), rotation.getServerYaw(), rotation.getServerPitch(), position.getBB(), entityCheck, additional);
    }
    
    public static RayTraceResult rayTraceEntities(final World world, final Entity from, final double range, final double posX, final double posY, final double posZ, final float yaw, final float pitch, final AxisAlignedBB fromBB, final Predicate<Entity> entityCheck, final Entity... additional) {
        final Vec3d eyePos = new Vec3d(posX, posY + from.getEyeHeight(), posZ);
        final Vec3d rot = RotationUtil.getVec3d(yaw, pitch);
        final Vec3d intercept = eyePos.add(rot.x * range, rot.y * range, rot.z * range);
        Entity pointedEntity = null;
        Vec3d hitVec = null;
        double distance = range;
        final AxisAlignedBB within = fromBB.expand(rot.x * range, rot.y * range, rot.z * range).grow(1.0, 1.0, 1.0);
        final Predicate<Entity> predicate = (Predicate<Entity>)((entityCheck == null) ? RayTracer.PREDICATE : Predicates.and((Predicate)RayTracer.PREDICATE, (Predicate)entityCheck));
        List<Entity> entities;
        if (Util.mc.isCallingFromMinecraftThread()) {
            entities = (List<Entity>)world.getEntitiesInAABBexcluding(from, within, (Predicate)predicate);
        }
        else {
            final AxisAlignedBB axisAlignedBB;
            final Predicate predicate2;
            entities = (List<Entity>)Util.mc.world.loadedEntityList.stream().filter(e -> e != null && e.getEntityBoundingBox().intersects(axisAlignedBB) && predicate2.test((Object)e)).collect(Collectors.toList());
        }
        for (final Entity entity : additional) {
            if (entity != null && entity.getEntityBoundingBox().intersects(within)) {
                entities.add(entity);
            }
        }
        for (final Entity entity2 : entities) {
            final AxisAlignedBB bb = entity2.getEntityBoundingBox().grow((double)entity2.getCollisionBorderSize());
            final RayTraceResult result = bb.calculateIntercept(eyePos, intercept);
            if (bb.contains(eyePos)) {
                if (distance < 0.0) {
                    continue;
                }
                pointedEntity = entity2;
                hitVec = ((result == null) ? eyePos : result.hitVec);
                distance = 0.0;
            }
            else {
                if (result == null) {
                    continue;
                }
                final double hitDistance = eyePos.distanceTo(result.hitVec);
                if (hitDistance >= distance && distance != 0.0) {
                    continue;
                }
                if (entity2.getLowestRidingEntity() == from.getLowestRidingEntity()) {
                    if (distance != 0.0) {
                        continue;
                    }
                    pointedEntity = entity2;
                    hitVec = result.hitVec;
                }
                else {
                    pointedEntity = entity2;
                    hitVec = result.hitVec;
                    distance = hitDistance;
                }
            }
        }
        if (pointedEntity != null && hitVec != null) {
            return new RayTraceResult(pointedEntity, hitVec);
        }
        return null;
    }
    
    public static RayTraceResult trace(final World world, final IBlockAccess access, final Vec3d start, final Vec3d end, final boolean stopOnLiquid, final boolean ignoreBlockWithoutBoundingBox, final boolean returnLastUncollidableBlock) {
        return trace(world, access, start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock, null);
    }
    
    public static RayTraceResult trace(final World world, final Vec3d start, final Vec3d end, final boolean stopOnLiquid, final boolean ignoreBlockWithoutBoundingBox, final boolean returnLastUncollidableBlock, final BiPredicate<Block, BlockPos> blockChecker) {
        return trace(world, (IBlockAccess)world, start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock, blockChecker);
    }
    
    public static RayTraceResult trace(final World world, final IBlockAccess access, final Vec3d start, final Vec3d end, final boolean stopOnLiquid, final boolean ignoreBlockWithoutBoundingBox, final boolean returnLastUncollidableBlock, final BiPredicate<Block, BlockPos> blockChecker) {
        return traceTri(world, access, start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock, (blockChecker == null) ? null : ((b, p, ef) -> blockChecker.test(b, p)));
    }
    
    public static RayTraceResult traceTri(final World world, final IBlockAccess access, final Vec3d start, final Vec3d end, final boolean stopOnLiquid, final boolean ignoreBlockWithoutBoundingBox, final boolean returnLastUncollidableBlock, final TriPredicate<Block, BlockPos, EnumFacing> blockChecker) {
        return traceTri(world, access, start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock, blockChecker, null);
    }
    
    public static RayTraceResult traceTri(final World world, final IBlockAccess access, final Vec3d start, final Vec3d end, final boolean stopOnLiquid, final boolean ignoreBlockWithoutBoundingBox, final boolean returnLastUncollidableBlock, final TriPredicate<Block, BlockPos, EnumFacing> blockChecker, final TriPredicate<Block, BlockPos, EnumFacing> collideCheck) {
        return traceTri(world, access, start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock, blockChecker, collideCheck, CollisionFunction.DEFAULT);
    }
    
    public static RayTraceResult traceTri(final World world, final IBlockAccess access, Vec3d start, final Vec3d end, final boolean stopOnLiquid, final boolean ignoreBlockWithoutBoundingBox, final boolean returnLastUncollidableBlock, final TriPredicate<Block, BlockPos, EnumFacing> blockChecker, final TriPredicate<Block, BlockPos, EnumFacing> collideCheck, final CollisionFunction crt) {
        if (Double.isNaN(start.x) || Double.isNaN(start.y) || Double.isNaN(start.z)) {
            return null;
        }
        if (!Double.isNaN(end.x) && !Double.isNaN(end.y) && !Double.isNaN(end.z)) {
            final int feX = MathHelper.floor(end.x);
            final int feY = MathHelper.floor(end.y);
            final int feZ = MathHelper.floor(end.z);
            int fsX = MathHelper.floor(start.x);
            int fsY = MathHelper.floor(start.y);
            int fsZ = MathHelper.floor(start.z);
            BlockPos pos = new BlockPos(fsX, fsY, fsZ);
            final IBlockState state = access.getBlockState(pos);
            final Block block = state.getBlock();
            if ((!ignoreBlockWithoutBoundingBox || state.getCollisionBoundingBox(access, pos) != Block.NULL_AABB) && (block.canCollideCheck(state, stopOnLiquid) || (collideCheck != null && collideCheck.test(block, pos, null))) && (blockChecker == null || blockChecker.test(block, pos, null))) {
                final RayTraceResult raytraceresult = crt.collisionRayTrace(state, world, pos, start, end);
                if (raytraceresult != null) {
                    return raytraceresult;
                }
            }
            RayTraceResult result = null;
            int steps = 200;
            while (steps-- >= 0) {
                if (Double.isNaN(start.x) || Double.isNaN(start.y) || Double.isNaN(start.z)) {
                    return null;
                }
                if (fsX == feX && fsY == feY && fsZ == feZ) {
                    return returnLastUncollidableBlock ? result : null;
                }
                boolean xEq = true;
                boolean yEq = true;
                boolean zEq = true;
                double x = 999.0;
                double y = 999.0;
                double z = 999.0;
                if (feX > fsX) {
                    x = fsX + 1.0;
                }
                else if (feX < fsX) {
                    x = fsX + 0.0;
                }
                else {
                    xEq = false;
                }
                if (feY > fsY) {
                    y = fsY + 1.0;
                }
                else if (feY < fsY) {
                    y = fsY + 0.0;
                }
                else {
                    yEq = false;
                }
                if (feZ > fsZ) {
                    z = fsZ + 1.0;
                }
                else if (feZ < fsZ) {
                    z = fsZ + 0.0;
                }
                else {
                    zEq = false;
                }
                double xOff = 999.0;
                double yOff = 999.0;
                double zOff = 999.0;
                final double diffX = end.x - start.x;
                final double diffY = end.y - start.y;
                final double diffZ = end.z - start.z;
                if (xEq) {
                    xOff = (x - start.x) / diffX;
                }
                if (yEq) {
                    yOff = (y - start.y) / diffY;
                }
                if (zEq) {
                    zOff = (z - start.z) / diffZ;
                }
                if (xOff == -0.0) {
                    xOff = -1.0E-4;
                }
                if (yOff == -0.0) {
                    yOff = -1.0E-4;
                }
                if (zOff == -0.0) {
                    zOff = -1.0E-4;
                }
                EnumFacing enumfacing;
                if (xOff < yOff && xOff < zOff) {
                    enumfacing = ((feX > fsX) ? EnumFacing.WEST : EnumFacing.EAST);
                    start = new Vec3d(x, start.y + diffY * xOff, start.z + diffZ * xOff);
                }
                else if (yOff < zOff) {
                    enumfacing = ((feY > fsY) ? EnumFacing.DOWN : EnumFacing.UP);
                    start = new Vec3d(start.x + diffX * yOff, y, start.z + diffZ * yOff);
                }
                else {
                    enumfacing = ((feZ > fsZ) ? EnumFacing.NORTH : EnumFacing.SOUTH);
                    start = new Vec3d(start.x + diffX * zOff, start.y + diffY * zOff, z);
                }
                fsX = MathHelper.floor(start.x) - ((enumfacing == EnumFacing.EAST) ? 1 : 0);
                fsY = MathHelper.floor(start.y) - ((enumfacing == EnumFacing.UP) ? 1 : 0);
                fsZ = MathHelper.floor(start.z) - ((enumfacing == EnumFacing.SOUTH) ? 1 : 0);
                pos = new BlockPos(fsX, fsY, fsZ);
                final IBlockState state2 = access.getBlockState(pos);
                final Block block2 = state2.getBlock();
                if (ignoreBlockWithoutBoundingBox && state2.getMaterial() != Material.PORTAL && state2.getCollisionBoundingBox(access, pos) == Block.NULL_AABB) {
                    continue;
                }
                if ((block2.canCollideCheck(state2, stopOnLiquid) || (collideCheck != null && collideCheck.test(block2, pos, enumfacing))) && (blockChecker == null || blockChecker.test(block2, pos, enumfacing))) {
                    final RayTraceResult raytraceresult2 = crt.collisionRayTrace(state2, world, pos, start, end);
                    if (raytraceresult2 != null) {
                        return raytraceresult2;
                    }
                    continue;
                }
                else {
                    result = new RayTraceResult(RayTraceResult.Type.MISS, start, enumfacing, pos);
                }
            }
            return returnLastUncollidableBlock ? result : null;
        }
        return null;
    }
    
    static {
        PREDICATE = Predicates.and(EntitySelectors.NOT_SPECTATING, e -> e != null && e.canBeCollidedWith());
    }
}
