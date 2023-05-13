//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.util.math.*;
import net.minecraft.util.math.*;

public class HelperRange
{
    private final AutoCrystal module;
    
    public HelperRange(final AutoCrystal module) {
        this.module = module;
    }
    
    public boolean isCrystalInRange(final Entity crystal) {
        return this.isCrystalInRange(crystal.posX, crystal.posY, crystal.posZ, 0);
    }
    
    public boolean isCrystalInRangeOfLastPosition(final Entity crystal) {
        return this.isCrystalInRange(crystal.posX, crystal.posY, crystal.posZ, Thunderhack.positionManager.getX(), Thunderhack.positionManager.getY(), Thunderhack.positionManager.getZ());
    }
    
    public boolean isCrystalInRange(final double crystalX, final double crystalY, final double crystalZ, final int ticks) {
        if ((boolean)this.module.smartBreakTrace.getValue() && this.isOutsideBreakTrace(crystalX, crystalY, crystalZ, ticks)) {
            return false;
        }
        if (this.module.ncpRange.getValue()) {
            final Entity breaker = (Entity)Util.mc.player;
            final double breakerX = breaker.posX + breaker.motionX * ticks;
            final double breakerY = breaker.posY + breaker.motionY * ticks;
            final double breakerZ = breaker.posZ + breaker.motionZ * ticks;
            return SmartRangeUtil.isInStrictBreakRange(crystalX, crystalY, crystalZ, MathUtil.square((double)(float)this.module.breakRange.getValue()), breakerX, breakerY, breakerZ);
        }
        return SmartRangeUtil.isInSmartRange(crystalX, crystalY, crystalZ, (Entity)Util.mc.player, MathUtil.square((double)(float)this.module.breakRange.getValue()), ticks);
    }
    
    public boolean isCrystalInRange(final double crystalX, final double crystalY, final double crystalZ, final double breakerX, final double breakerY, final double breakerZ) {
        if ((boolean)this.module.smartBreakTrace.getValue() && !this.isCrystalInBreakTrace(crystalX, crystalY, crystalZ, breakerX, breakerY, breakerZ)) {
            return false;
        }
        if (this.module.ncpRange.getValue()) {
            return SmartRangeUtil.isInStrictBreakRange(crystalX, crystalY, crystalZ, MathUtil.square((double)(float)this.module.breakRange.getValue()), breakerX, breakerY, breakerZ);
        }
        return distanceSq(crystalX, crystalY, crystalZ, breakerX, breakerY, breakerZ) < MathUtil.square((double)(float)this.module.breakRange.getValue());
    }
    
    public static double distanceSq(final double x, final double y, final double z, final Entity entity) {
        return distanceSq(x, y, z, entity.posX, entity.posY, entity.posZ);
    }
    
    public static double distanceSq(final double x, final double y, final double z, final double x1, final double y1, final double z1) {
        final double xDist = x - x1;
        final double yDist = y - y1;
        final double zDist = z - z1;
        return xDist * xDist + yDist * yDist + zDist * zDist;
    }
    
    public boolean isCrystalOutsideNegativeRange(final BlockPos pos) {
        final int negativeTicks = (int)this.module.negativeTicks.getValue();
        if (negativeTicks == 0) {
            return false;
        }
        if ((boolean)this.module.negativeBreakTrace.getValue() && this.isOutsideBreakTrace(pos, negativeTicks)) {
            return true;
        }
        if (this.module.ncpRange.getValue()) {
            final Entity breaker = (Entity)Util.mc.player;
            final double breakerX = breaker.posX + breaker.motionX * negativeTicks;
            final double breakerY = breaker.posY + breaker.motionY * negativeTicks;
            final double breakerZ = breaker.posZ + breaker.motionZ * negativeTicks;
            return !SmartRangeUtil.isInStrictBreakRange(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5, MathUtil.square((double)(float)this.module.breakRange.getValue()), breakerX, breakerY, breakerZ);
        }
        return !SmartRangeUtil.isInSmartRange(pos, (Entity)Util.mc.player, MathUtil.square((double)(float)this.module.breakRange.getValue()), negativeTicks);
    }
    
    public boolean isOutsideBreakTrace(final BlockPos pos, final int ticks) {
        return this.isOutsideBreakTrace(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, ticks);
    }
    
    public boolean isOutsideBreakTrace(final double x, final double y, final double z, final int ticks) {
        final Entity breaker = (Entity)Util.mc.player;
        final double breakerX = breaker.posX + breaker.motionX * ticks;
        final double breakerY = breaker.posY + breaker.motionY * ticks;
        final double breakerZ = breaker.posZ + breaker.motionZ * ticks;
        return !this.isCrystalInBreakTrace(x, y, z, breakerX, breakerY, breakerZ);
    }
    
    public boolean isCrystalInBreakTrace(final double crystalX, final double crystalY, final double crystalZ, final double breakerX, final double breakerY, final double breakerZ) {
        return distanceSq(crystalX, crystalY, crystalZ, breakerX, breakerY, breakerZ) < MathUtil.square((double)(float)this.module.breakTrace.getValue()) || Util.mc.world.rayTraceBlocks(new Vec3d(breakerX, breakerY + Util.mc.player.getEyeHeight(), breakerZ), new Vec3d(crystalX, crystalY + 1.7, crystalZ), false, true, false) == null;
    }
}
