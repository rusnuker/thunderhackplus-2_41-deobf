//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.function.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.*;
import java.util.*;
import com.mrzak34.thunderhack.mixin.ducks.*;

public class CalculationMotion extends AbstractCalculation<CrystalDataMotion>
{
    public CalculationMotion(final AutoCrystal module, final List<Entity> entities, final List<EntityPlayer> players) {
        super(module, (List)entities, (List)players, new BlockPos[0]);
    }
    
    public static boolean isLegit(final Entity entity, final Entity... additional) {
        final RayTraceResult result = RayTracer.rayTraceEntities((World)Util.mc.world, (Entity)Util.mc.player, Util.mc.player.getDistance(entity) + 1.0, Thunderhack.positionManager, Thunderhack.rotationManager, (Predicate<Entity>)(e -> e != null && e.equals((Object)entity)), additional);
        return result != null && result.entityHit != null && (entity.equals((Object)result.entityHit) || (additional != null && additional.length != 0 && Arrays.stream(additional).anyMatch(e -> result.entityHit.equals((Object)e))));
    }
    
    public static boolean isLegit(final BlockPos pos) {
        return isLegit(pos, null);
    }
    
    public static boolean isLegit(final BlockPos pos, final EnumFacing facing) {
        return isLegit(pos, facing, (IBlockAccess)Util.mc.world);
    }
    
    public static boolean isLegit(final BlockPos pos, final EnumFacing facing, final IBlockAccess world) {
        final RayTraceResult ray = rayTraceTo(pos, world);
        return ray != null && ray.getBlockPos() != null && ray.getBlockPos().equals((Object)pos) && (facing == null || ray.sideHit == facing);
    }
    
    public static RayTraceResult rayTraceTo(final BlockPos pos, final IBlockAccess world) {
        return rayTraceTo(pos, world, (b, p) -> p.equals((Object)pos));
    }
    
    public static RayTraceResult rayTraceTo(final BlockPos pos, final IBlockAccess world, final BiPredicate<Block, BlockPos> check) {
        return rayTraceWithYP(pos, world, Thunderhack.rotationManager.getServerYaw(), Thunderhack.rotationManager.getServerPitch(), check);
    }
    
    public static RayTraceResult rayTraceWithYP(final BlockPos pos, final IBlockAccess world, final float yaw, final float pitch, final BiPredicate<Block, BlockPos> check) {
        final Entity from = (Entity)Util.mc.player;
        final Vec3d start = Thunderhack.positionManager.getVec().add(0.0, (double)from.getEyeHeight(), 0.0);
        final Vec3d look = RotationUtil.getVec3d(yaw, pitch);
        final double d = from.getDistance(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) + 1.0;
        final Vec3d end = start.add(look.x * d, look.y * d, look.z * d);
        return RayTracer.trace((World)Util.mc.world, world, start, end, true, false, true, check);
    }
    
    protected IBreakHelper<CrystalDataMotion> getBreakHelper() {
        return (IBreakHelper<CrystalDataMotion>)this.module.breakHelperMotion;
    }
    
    protected boolean evaluate(final BreakData<CrystalDataMotion> breakData) {
        boolean slowReset = false;
        AutoCrystal.BreakValidity validity;
        if (this.breakData.getAntiTotem() != null && (validity = HelperUtil.isValid(this.module, this.breakData.getAntiTotem())) != AutoCrystal.BreakValidity.INVALID) {
            this.attack(this.breakData.getAntiTotem(), validity);
            this.module.breakTimer.reset((int)this.module.breakDelay.getValue());
            this.module.antiTotemHelper.setTarget((EntityPlayer)null);
            this.module.antiTotemHelper.setTargetPos((BlockPos)null);
        }
        else {
            final int packets = (int)(this.module.noRotateNigga(AutoCrystal.ACRotate.Break) ? this.module.packets.getValue() : 1);
            CrystalDataMotion firstRotation = null;
            final List<CrystalDataMotion> valids = new ArrayList<CrystalDataMotion>(packets);
            for (final CrystalDataMotion data : this.breakData.getData()) {
                if (EntityUtil.isDead(data.getCrystal())) {
                    continue;
                }
                if (data.getTiming() == CrystalDataMotion.Timing.NONE) {
                    continue;
                }
                validity = this.isValid(this.module, data);
                if (validity == AutoCrystal.BreakValidity.VALID && valids.size() < packets) {
                    valids.add(data);
                }
                else {
                    if (validity != AutoCrystal.BreakValidity.ROTATIONS || (data.getTiming() != CrystalDataMotion.Timing.BOTH && data.getTiming() != CrystalDataMotion.Timing.POST) || firstRotation != null) {
                        continue;
                    }
                    firstRotation = data;
                }
            }
            final int slowDelay = (int)this.module.slowBreakDelay.getValue();
            final float slow = (float)this.module.slowBreakDamage.getValue();
            if (valids.isEmpty()) {
                if (firstRotation != null && (this.module.shouldDanger() || !(slowReset = (firstRotation.getDamage() <= slow)) || this.module.breakTimer.passed(slowDelay))) {
                    this.attack(firstRotation.getCrystal(), AutoCrystal.BreakValidity.ROTATIONS);
                }
            }
            else {
                slowReset = !this.module.shouldDanger();
                for (final CrystalDataMotion v : valids) {
                    final boolean high = v.getDamage() > (float)this.module.slowBreakDamage.getValue();
                    if (high || this.module.breakTimer.passed((int)this.module.slowBreakDelay.getValue())) {
                        slowReset = (slowReset && !high);
                        if (v.getTiming() == CrystalDataMotion.Timing.POST || (v.getTiming() == CrystalDataMotion.Timing.BOTH && v.getPostSelf() < v.getSelfDmg())) {
                            this.attackPost(v.getCrystal());
                        }
                        else {
                            this.attack(v.getCrystal(), AutoCrystal.BreakValidity.VALID);
                        }
                    }
                }
            }
        }
        if (this.attacking) {
            this.module.breakTimer.reset(slowReset ? ((long)(int)this.module.slowBreakDelay.getValue()) : ((long)(int)this.module.breakDelay.getValue()));
        }
        return this.rotating && !this.module.noRotateNigga(AutoCrystal.ACRotate.Place);
    }
    
    protected void attackPost(final Entity entity) {
        this.attacking = true;
        this.scheduling = true;
        this.rotating = !this.module.noRotateNigga(AutoCrystal.ACRotate.Break);
        final MutableWrapper<Boolean> attacked = new MutableWrapper<Boolean>(false);
        final Runnable post = this.module.rotationHelper.post(entity, attacked);
        this.module.post.add(post);
    }
    
    private AutoCrystal.BreakValidity isValid(final AutoCrystal module, final CrystalDataMotion dataMotion) {
        final Entity crystal = dataMotion.getCrystal();
        if ((int)module.existed.getValue() != 0 && System.currentTimeMillis() - ((IEntity)crystal).getTimeStampT() + (module.pingExisted.getValue() ? (Thunderhack.serverManager.getPing() / 2.0) : 0.0) < (int)module.existed.getValue()) {
            return AutoCrystal.BreakValidity.INVALID;
        }
        if (module.noRotateNigga(AutoCrystal.ACRotate.Break) || module.isNotCheckingRotations() || (isLegit(crystal, crystal) && AutoCrystal.POSITION_HISTORY.arePreviousRotationsLegit(crystal, (int)module.rotationTicks.getValue(), true))) {
            return AutoCrystal.BreakValidity.VALID;
        }
        return AutoCrystal.BreakValidity.ROTATIONS;
    }
}
