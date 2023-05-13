//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.rotations;

import java.util.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.client.*;
import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.setting.*;

public class RayTracingUtils
{
    public static ArrayList<Vec3d> getHitBoxPointsJitter(final Vec3d position, final float fakeBoxScale) {
        final float head_height = 1.6f + interpolateRandom(-0.4f, 0.2f);
        final float chest_height = 0.8f + interpolateRandom(-0.2f, 0.2f);
        final float leggs_height = 0.225f + interpolateRandom(-0.1f, 0.1f);
        final Vec3d head1 = position.add((double)(-fakeBoxScale), (double)head_height, (double)fakeBoxScale);
        final Vec3d head2 = position.add(0.0, (double)head_height, (double)fakeBoxScale);
        final Vec3d head3 = position.add((double)fakeBoxScale, (double)head_height, (double)fakeBoxScale);
        final Vec3d head4 = position.add((double)(-fakeBoxScale), (double)head_height, 0.0);
        final Vec3d head5 = position.add((double)fakeBoxScale, (double)head_height, 0.0);
        final Vec3d head6 = position.add((double)(-fakeBoxScale), (double)head_height, (double)(-fakeBoxScale));
        final Vec3d head7 = position.add(0.0, (double)head_height, (double)(-fakeBoxScale));
        final Vec3d head8 = position.add((double)fakeBoxScale, (double)head_height, (double)(-fakeBoxScale));
        final Vec3d chest1 = position.add((double)(-fakeBoxScale), (double)chest_height, (double)fakeBoxScale);
        final Vec3d chest2 = position.add(0.0, (double)chest_height, (double)fakeBoxScale);
        final Vec3d chest3 = position.add((double)fakeBoxScale, (double)chest_height, (double)fakeBoxScale);
        final Vec3d chest4 = position.add((double)(-fakeBoxScale), (double)chest_height, 0.0);
        final Vec3d chest5 = position.add((double)fakeBoxScale, (double)chest_height, 0.0);
        final Vec3d chest6 = position.add((double)(-fakeBoxScale), (double)chest_height, (double)(-fakeBoxScale));
        final Vec3d chest7 = position.add(0.0, (double)chest_height, (double)(-fakeBoxScale));
        final Vec3d chest8 = position.add((double)fakeBoxScale, (double)chest_height, (double)(-fakeBoxScale));
        final Vec3d legs1 = position.add((double)(-fakeBoxScale), (double)leggs_height, (double)fakeBoxScale);
        final Vec3d legs2 = position.add(0.0, (double)leggs_height, (double)fakeBoxScale);
        final Vec3d legs3 = position.add((double)fakeBoxScale, (double)leggs_height, (double)fakeBoxScale);
        final Vec3d legs4 = position.add((double)(-fakeBoxScale), (double)leggs_height, 0.0);
        final Vec3d legs5 = position.add((double)fakeBoxScale, (double)leggs_height, 0.0);
        final Vec3d legs6 = position.add((double)(-fakeBoxScale), (double)leggs_height, (double)(-fakeBoxScale));
        final Vec3d legs7 = position.add(0.0, (double)leggs_height, (double)(-fakeBoxScale));
        final Vec3d legs8 = position.add((double)fakeBoxScale, (double)leggs_height, (double)(-fakeBoxScale));
        return new ArrayList<Vec3d>(Arrays.asList(head1, head2, head3, head4, head5, head6, head7, head8, chest1, chest2, chest3, chest4, chest5, chest6, chest7, chest8, legs1, legs2, legs3, legs4, legs5, legs6, legs7, legs8));
    }
    
    public static ArrayList<Vec3d> getHitBoxPointsNonJitter(final Vec3d position, final float fakeBoxScale) {
        final float head_height = 1.6f;
        final float chest_height = 0.8f;
        final float leggs_height = 0.225f;
        final Vec3d head1 = position.add((double)(-fakeBoxScale), (double)head_height, (double)fakeBoxScale);
        final Vec3d head2 = position.add(0.0, (double)head_height, (double)fakeBoxScale);
        final Vec3d head3 = position.add((double)fakeBoxScale, (double)head_height, (double)fakeBoxScale);
        final Vec3d head4 = position.add((double)(-fakeBoxScale), (double)head_height, 0.0);
        final Vec3d head5 = position.add((double)fakeBoxScale, (double)head_height, 0.0);
        final Vec3d head6 = position.add((double)(-fakeBoxScale), (double)head_height, (double)(-fakeBoxScale));
        final Vec3d head7 = position.add(0.0, (double)head_height, (double)(-fakeBoxScale));
        final Vec3d head8 = position.add((double)fakeBoxScale, (double)head_height, (double)(-fakeBoxScale));
        final Vec3d chest1 = position.add((double)(-fakeBoxScale), (double)chest_height, (double)fakeBoxScale);
        final Vec3d chest2 = position.add(0.0, (double)chest_height, (double)fakeBoxScale);
        final Vec3d chest3 = position.add((double)fakeBoxScale, (double)chest_height, (double)fakeBoxScale);
        final Vec3d chest4 = position.add((double)(-fakeBoxScale), (double)chest_height, 0.0);
        final Vec3d chest5 = position.add((double)fakeBoxScale, (double)chest_height, 0.0);
        final Vec3d chest6 = position.add((double)(-fakeBoxScale), (double)chest_height, (double)(-fakeBoxScale));
        final Vec3d chest7 = position.add(0.0, (double)chest_height, (double)(-fakeBoxScale));
        final Vec3d chest8 = position.add((double)fakeBoxScale, (double)chest_height, (double)(-fakeBoxScale));
        final Vec3d legs1 = position.add((double)(-fakeBoxScale), (double)leggs_height, (double)fakeBoxScale);
        final Vec3d legs2 = position.add(0.0, (double)leggs_height, (double)fakeBoxScale);
        final Vec3d legs3 = position.add((double)fakeBoxScale, (double)leggs_height, (double)fakeBoxScale);
        final Vec3d legs4 = position.add((double)(-fakeBoxScale), (double)leggs_height, 0.0);
        final Vec3d legs5 = position.add((double)fakeBoxScale, (double)leggs_height, 0.0);
        final Vec3d legs6 = position.add((double)(-fakeBoxScale), (double)leggs_height, (double)(-fakeBoxScale));
        final Vec3d legs7 = position.add(0.0, (double)leggs_height, (double)(-fakeBoxScale));
        final Vec3d legs8 = position.add((double)fakeBoxScale, (double)leggs_height, (double)(-fakeBoxScale));
        return new ArrayList<Vec3d>(Arrays.asList(head1, head2, head3, head4, head5, head6, head7, head8, chest1, chest2, chest3, chest4, chest5, chest6, chest7, chest8, legs1, legs2, legs3, legs4, legs5, legs6, legs7, legs8));
    }
    
    public static ArrayList<Vec3d> getHitBoxPointsOldJitter(final Vec3d position, final float fakeBoxScale) {
        final float head_height = 1.6f + interpolateRandom(-0.8f, 0.2f);
        final float chest_height = 0.8f + interpolateRandom(-0.6f, 0.2f);
        final float leggs_height = 0.15f + interpolateRandom(-0.1f, 0.1f);
        final Vec3d head1 = position.add(0.0, (double)head_height, 0.0);
        final Vec3d chest1 = position.add(0.0, (double)chest_height, 0.0);
        final Vec3d legs1 = position.add(0.0, (double)leggs_height, 0.0);
        return new ArrayList<Vec3d>(Arrays.asList(head1, chest1, legs1));
    }
    
    public static ArrayList<Vec3d> getHitBoxPointsOld(final Vec3d position, final float fakeBoxScale) {
        final float head_height = 1.6f;
        final float chest_height = 0.8f;
        final float leggs_height = 0.15f;
        final Vec3d head1 = position.add(0.0, (double)head_height, 0.0);
        final Vec3d chest1 = position.add(0.0, (double)chest_height, 0.0);
        final Vec3d legs1 = position.add(0.0, (double)leggs_height, 0.0);
        return new ArrayList<Vec3d>(Arrays.asList(head1, chest1, legs1));
    }
    
    public static float interpolateRandom(final float var0, final float var1) {
        return (float)(var0 + (var1 - var0) * Math.random());
    }
    
    public static Entity getPointedEntity(final Vec2f rot, final double dst, final boolean walls, final Entity target) {
        final double d0 = dst;
        RayTraceResult objectMouseOver = rayTrace(d0, rot.x, rot.y, walls);
        final Vec3d vec3d = Util.mc.player.getPositionEyes(1.0f);
        final boolean flag = false;
        double d2 = d0;
        if (objectMouseOver != null) {
            d2 = objectMouseOver.hitVec.distanceTo(vec3d);
        }
        final Vec3d vec3d2 = getLook(rot.x, rot.y);
        final Vec3d vec3d3 = vec3d.add(vec3d2.x * d0, vec3d2.y * d0, vec3d2.z * d0);
        Entity pointedEntity = null;
        Vec3d vec3d4 = null;
        double d3 = d2;
        final Entity entity1 = target;
        final AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)entity1.getCollisionBorderSize(), (double)entity1.getCollisionBorderSize(), (double)entity1.getCollisionBorderSize());
        final RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d3);
        if (axisalignedbb.contains(vec3d)) {
            if (d3 >= 0.0) {
                pointedEntity = entity1;
                vec3d4 = ((raytraceresult == null) ? vec3d : raytraceresult.hitVec);
                d3 = 0.0;
            }
        }
        else if (raytraceresult != null) {
            final double d4 = vec3d.distanceTo(raytraceresult.hitVec);
            if (d4 < d3 || d3 == 0.0) {
                final boolean flag2 = false;
                if (!flag2 && entity1.getLowestRidingEntity() == Util.mc.player.getLowestRidingEntity()) {
                    if (d3 == 0.0) {
                        pointedEntity = entity1;
                        vec3d4 = raytraceresult.hitVec;
                    }
                }
                else {
                    pointedEntity = entity1;
                    vec3d4 = raytraceresult.hitVec;
                    d3 = d4;
                }
            }
        }
        if (pointedEntity != null && flag && vec3d.distanceTo(vec3d4) > dst) {
            pointedEntity = null;
            objectMouseOver = new RayTraceResult(RayTraceResult.Type.MISS, vec3d4, (EnumFacing)null, new BlockPos(vec3d4));
        }
        if (pointedEntity != null && (d3 < d2 || objectMouseOver == null)) {
            objectMouseOver = new RayTraceResult(pointedEntity, vec3d4);
        }
        return (objectMouseOver != null) ? ((objectMouseOver.entityHit instanceof Entity) ? objectMouseOver.entityHit : null) : null;
    }
    
    public static RayTraceResult rayTrace(final double blockReachDistance, final float yaw, final float pitch, final boolean walls) {
        if (!walls) {
            return null;
        }
        final Vec3d vec3d = Util.mc.player.getPositionEyes(1.0f);
        final Vec3d vec3d2 = getLook(yaw, pitch);
        final Vec3d vec3d3 = vec3d.add(vec3d2.x * blockReachDistance, vec3d2.y * blockReachDistance, vec3d2.z * blockReachDistance);
        return Util.mc.world.rayTraceBlocks(vec3d, vec3d3, true, true, true);
    }
    
    static Vec3d getVectorForRotation(final float pitch, final float yaw) {
        final float f = MathHelper.cos(-yaw * 0.017453292f - 3.1415927f);
        final float f2 = MathHelper.sin(-yaw * 0.017453292f - 3.1415927f);
        final float f3 = -MathHelper.cos(-pitch * 0.017453292f);
        final float f4 = MathHelper.sin(-pitch * 0.017453292f);
        return new Vec3d((double)(f2 * f3), (double)f4, (double)(f * f3));
    }
    
    public static Entity getMouseOver(final Entity target, final float yaw, final float pitch, final double distance, final boolean ignoreWalls) {
        final Entity entity = Util.mc.getRenderViewEntity();
        if (entity == null || Util.mc.world == null) {
            return null;
        }
        RayTraceResult objectMouseOver = ignoreWalls ? null : rayTrace(distance, yaw, pitch);
        final Vec3d vec3d = entity.getPositionEyes(1.0f);
        boolean flag = false;
        double d1 = distance;
        if (distance > 3.0) {
            flag = true;
        }
        if (objectMouseOver != null) {
            d1 = objectMouseOver.hitVec.distanceTo(vec3d);
        }
        final Vec3d vec3d2 = getVectorForRotation(pitch, yaw);
        final Vec3d vec3d3 = vec3d.add(vec3d2.x * distance, vec3d2.y * distance, vec3d2.z * distance);
        Entity pointedEntity = null;
        Vec3d vec3d4 = null;
        double d2 = d1;
        final AxisAlignedBB axisalignedbb = target.getEntityBoundingBox().expand((double)target.getCollisionBorderSize(), (double)target.getCollisionBorderSize(), (double)target.getCollisionBorderSize());
        final RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d3);
        if (axisalignedbb.contains(vec3d)) {
            if (d2 >= 0.0) {
                pointedEntity = target;
                vec3d4 = ((raytraceresult == null) ? vec3d : raytraceresult.hitVec);
                d2 = 0.0;
            }
        }
        else if (raytraceresult != null) {
            final double d3 = vec3d.distanceTo(raytraceresult.hitVec);
            if (d3 < d2 || d2 == 0.0) {
                final boolean flag2 = false;
                if (!flag2 && target.getLowestRidingEntity() == entity.getLowestRidingEntity()) {
                    if (d2 == 0.0) {
                        pointedEntity = target;
                        vec3d4 = raytraceresult.hitVec;
                    }
                }
                else {
                    pointedEntity = target;
                    vec3d4 = raytraceresult.hitVec;
                    d2 = d3;
                }
            }
        }
        if (pointedEntity != null && flag && vec3d.distanceTo(vec3d4) > distance) {
            pointedEntity = null;
            objectMouseOver = new RayTraceResult(RayTraceResult.Type.MISS, vec3d4, (EnumFacing)null, new BlockPos(vec3d4));
        }
        if (pointedEntity != null && (d2 < d1 || objectMouseOver == null)) {
            objectMouseOver = new RayTraceResult(pointedEntity, vec3d4);
        }
        if (objectMouseOver == null) {
            return null;
        }
        return objectMouseOver.entityHit;
    }
    
    public static RayTraceResult rayTrace(final double blockReachDistance, final float yaw, final float pitch) {
        final Vec3d vec3d = Minecraft.getMinecraft().player.getPositionEyes(1.0f);
        final Vec3d vec3d2 = getVectorForRotation(pitch, yaw);
        final Vec3d vec3d3 = vec3d.add(vec3d2.x * blockReachDistance, vec3d2.y * blockReachDistance, vec3d2.z * blockReachDistance);
        return Minecraft.getMinecraft().world.rayTraceBlocks(vec3d, vec3d3, true, true, true);
    }
    
    static Vec3d getLook(final float yaw, final float pitch) {
        return getVectorForRotation(pitch, yaw);
    }
    
    public static ArrayList<Vec3d> getHitBoxPoints(final Vec3d position, final float fakeBoxScale) {
        final Setting<Aura.RayTracingMode> mode = (Setting<Aura.RayTracingMode>)((Aura)Thunderhack.moduleManager.getModuleByClass((Class)Aura.class)).rayTracing;
        switch ((Aura.RayTracingMode)mode.getValue()) {
            case New: {
                return getHitBoxPointsNonJitter(position, fakeBoxScale);
            }
            case Old: {
                return getHitBoxPointsOld(position, fakeBoxScale);
            }
            case OldJitter: {
                return getHitBoxPointsOldJitter(position, fakeBoxScale);
            }
            case NewJitter: {
                return getHitBoxPointsJitter(position, fakeBoxScale);
            }
            default: {
                return getHitBoxPointsNonJitter(position, fakeBoxScale);
            }
        }
    }
    
    public static Vec3d getVecTarget(final Entity target, final double distance) {
        Vec3d vec = target.getPositionVector().add(new Vec3d(0.0, MathHelper.clamp(target.getEyeHeight() * (Util.mc.player.getDistance(target) / (distance + target.width)), 0.2, (double)Util.mc.player.getEyeHeight()), 0.0));
        if (!isHitBoxVisible(vec)) {
            for (double i = target.width * 0.05; i <= target.width * 0.95; i += target.width * 0.9 / 8.0) {
                for (double j = target.width * 0.05; j <= target.width * 0.95; j += target.width * 0.9 / 8.0) {
                    for (double k = 0.0; k <= target.height; k += target.height / 8.0f) {
                        if (isHitBoxVisible(new Vec3d(i, k, j).add(target.getPositionVector().add(new Vec3d((double)(-target.width / 2.0f), 0.0, (double)(-target.width / 2.0f)))))) {
                            vec = new Vec3d(i, k, j).add(target.getPositionVector().add(new Vec3d((double)(-target.width / 2.0f), 0.0, (double)(-target.width / 2.0f))));
                            break;
                        }
                    }
                }
            }
        }
        if (getDistanceFromHead(vec) > distance * distance) {
            return null;
        }
        return vec;
    }
    
    public static boolean isHitBoxVisible(final Vec3d vec3d) {
        final Vec3d eyesPos = new Vec3d(Util.mc.player.posX, Util.mc.player.getEntityBoundingBox().minY + Util.mc.player.getEyeHeight(), Util.mc.player.posZ);
        return Util.mc.world.rayTraceBlocks(eyesPos, vec3d, false, true, false) == null;
    }
    
    public static float getDistanceFromHead(final Vec3d d1) {
        final double x = d1.x - Util.mc.player.posX;
        final double y = d1.y - Util.mc.player.getPositionEyes(1.0f).y;
        final double z = d1.z - Util.mc.player.posZ;
        return (float)(Math.pow(x, 2.0) + Math.pow(y, 2.0) + Math.pow(z, 2.0));
    }
}
