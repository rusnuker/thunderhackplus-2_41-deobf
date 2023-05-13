//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;

public class DamageHelper
{
    private final Setting<Boolean> terrainCalc;
    private final Setting<Integer> bExtrapolation;
    private final Setting<Integer> pExtrapolation;
    private final Setting<Boolean> selfExtrapolation;
    private final Setting<Boolean> obbyTerrain;
    private final ExtrapolationHelper positionHelper;
    private final AutoCrystal module;
    
    public DamageHelper(final AutoCrystal module, final ExtrapolationHelper positionHelper, final Setting<Boolean> terrainCalc, final Setting<Integer> extrapolation, final Setting<Integer> bExtrapolation, final Setting<Boolean> selfExtrapolation, final Setting<Boolean> obbyTerrain) {
        this.module = module;
        this.positionHelper = positionHelper;
        this.terrainCalc = terrainCalc;
        this.pExtrapolation = extrapolation;
        this.bExtrapolation = bExtrapolation;
        this.selfExtrapolation = selfExtrapolation;
        this.obbyTerrain = obbyTerrain;
    }
    
    public float getDamage(final Entity crystal) {
        if (this.module.isSuicideModule()) {
            return 0.0f;
        }
        return DamageUtil.calculate(crystal.posX, crystal.posY, crystal.posZ, Thunderhack.positionManager.getBB(), (EntityLivingBase)Util.mc.player);
    }
    
    public float getDamage(final Entity crystal, final AxisAlignedBB bb) {
        if (this.module.isSuicideModule()) {
            return 0.0f;
        }
        return DamageUtil.calculate(crystal.posX, crystal.posY, crystal.posZ, bb, (EntityLivingBase)Util.mc.player);
    }
    
    public float getDamage(final Entity crystal, final EntityLivingBase base) {
        return this.getDamage(crystal.posX, crystal.posY, crystal.posZ, base, (IBlockAccess)Util.mc.world, (int)this.bExtrapolation.getValue(), (boolean)this.module.avgBreakExtra.getValue(), false, false, (boolean)this.terrainCalc.getValue());
    }
    
    public float getDamage(final BlockPos pos) {
        if (this.module.isSuicideModule()) {
            return 0.0f;
        }
        return this.getDamage(pos, (EntityLivingBase)Util.mc.player, (IBlockAccess)Util.mc.world, (int)this.pExtrapolation.getValue(), (boolean)this.module.avgPlaceDamage.getValue(), true, (boolean)this.terrainCalc.getValue());
    }
    
    public float getDamage(final BlockPos pos, final EntityLivingBase base) {
        return this.getDamage(pos, base, (IBlockAccess)Util.mc.world, (int)this.pExtrapolation.getValue(), (boolean)this.module.avgPlaceDamage.getValue(), false, (boolean)this.terrainCalc.getValue());
    }
    
    public float getObbyDamage(final BlockPos pos, final IBlockAccess world) {
        if (this.module.isSuicideModule()) {
            return 0.0f;
        }
        return this.getDamage(pos, (EntityLivingBase)Util.mc.player, world, (int)this.pExtrapolation.getValue(), (boolean)this.module.avgPlaceDamage.getValue(), true, (boolean)this.obbyTerrain.getValue());
    }
    
    public float getObbyDamage(final BlockPos pos, final EntityLivingBase base, final IBlockAccess world) {
        return this.getDamage(pos, base, world, (int)this.pExtrapolation.getValue(), (boolean)this.module.avgPlaceDamage.getValue(), false, (boolean)this.obbyTerrain.getValue());
    }
    
    private float getDamage(final BlockPos pos, final EntityLivingBase base, final IBlockAccess world, final int ticks, final boolean avg, final boolean self, final boolean terrain) {
        return this.getDamage(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, base, world, ticks, avg, self, true, terrain);
    }
    
    private float getDamage(final double x, final double y, final double z, final EntityLivingBase base, final IBlockAccess world, final int ticks, final boolean avg, final boolean self, final boolean place, final boolean terrain) {
        final MotionTracker tracker;
        if (ticks == 0 || (self && !(boolean)this.selfExtrapolation.getValue()) || (tracker = (place ? this.positionHelper.getTrackerFromEntity((Entity)base) : this.positionHelper.getBreakTrackerFromEntity((Entity)base))) == null || !tracker.active) {
            return DamageUtil.calculate(x, y, z, base.getEntityBoundingBox(), base, world, terrain);
        }
        final float dmg = DamageUtil.calculate(x, y, z, tracker.getEntityBoundingBox(), base, world, terrain);
        if (avg) {
            final double extraWeight = (double)(place ? this.module.placeExtraWeight.getValue() : ((double)this.module.breakExtraWeight.getValue()));
            final double normWeight = (double)(place ? this.module.placeNormalWeight.getValue() : ((double)this.module.breakNormalWeight.getValue()));
            final float normDmg = DamageUtil.calculate(x, y, z, base.getEntityBoundingBox(), base, world, terrain);
            return (float)((normDmg * normWeight + dmg * extraWeight) / 2.0);
        }
        return dmg;
    }
}
