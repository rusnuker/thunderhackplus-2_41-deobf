//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.util.math.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.*;

public class HelperPlace
{
    private final AutoCrystal module;
    
    public HelperPlace(final AutoCrystal module) {
        this.module = module;
    }
    
    public PlaceData getData(final List<EntityPlayer> general, final List<EntityPlayer> players, final List<EntityPlayer> enemies, final List<EntityPlayer> friends, final List<Entity> entities, final float minDamage, final Set<BlockPos> blackList, final double maxY) {
        final PlaceData data = new PlaceData(minDamage);
        final EntityPlayer target = (EntityPlayer)(this.module.isSuicideModule() ? Util.mc.player : this.module.getTTRG((List)players, (List)enemies, (Float)this.module.targetRange.getValue()));
        if (target == null && this.module.targetMode.getValue() != AutoCrystal.Target.Damage) {
            return data;
        }
        data.setTarget(target);
        this.evaluate(data, general, friends, entities, blackList, maxY);
        data.addAllCorrespondingData();
        return data;
    }
    
    private void evaluate(final PlaceData data, final List<EntityPlayer> players, final List<EntityPlayer> friends, final List<Entity> entities, final Set<BlockPos> blackList, final double maxY) {
        final boolean obby = (boolean)this.module.obsidian.getValue() && this.module.obbyTimer.passedMs((int)this.module.obbyDelay.getValue()) && (InventoryUtil.isHolding(Blocks.OBSIDIAN) || ((boolean)this.module.obbySwitch.getValue() && InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN) != -1));
        switch ((AutoCrystal.PreCalc)this.module.preCalc.getValue()) {
            case Damage: {
                for (final EntityPlayer player : players) {
                    this.preCalc(data, player, obby, entities, friends, blackList);
                }
            }
            case Target: {
                if (data.getTarget() == null) {
                    if (data.getData().isEmpty()) {
                        break;
                    }
                }
                else {
                    this.preCalc(data, data.getTarget(), obby, entities, friends, blackList);
                }
                for (final PositionData positionData : data.getData()) {
                    if (positionData.getMaxDamage() > data.getMinDamage() && positionData.getMaxDamage() > (float)this.module.preCalcDamage.getValue()) {
                        return;
                    }
                }
                break;
            }
        }
        final BlockPos middle = Util.mc.player.getPosition();
        for (int maxRadius = Sphere.getRadius((float)this.module.placeRange.getValue()), i = 1; i < maxRadius; ++i) {
            this.calc(middle.add(Sphere.get(i)), data, players, friends, entities, obby, blackList, maxY);
        }
    }
    
    private void preCalc(final PlaceData data, final EntityPlayer player, final boolean obby, final List<Entity> entities, final List<EntityPlayer> friends, final Set<BlockPos> blackList) {
        MotionTracker extrapolationEntity = null;
        switch ((AutoCrystal.ExtrapolationType)this.module.preCalcExtra.getValue()) {
            case Place: {
                extrapolationEntity = (((int)this.module.extrapol.getValue() == 0) ? null : this.module.extrapolationHelper.getTrackerFromEntity((Entity)player));
                break;
            }
            case Break: {
                extrapolationEntity = (((int)this.module.bExtrapol.getValue() == 0) ? null : this.module.extrapolationHelper.getBreakTrackerFromEntity((Entity)player));
                break;
            }
            case Block: {
                extrapolationEntity = (((int)this.module.blockExtrapol.getValue() == 0) ? null : this.module.extrapolationHelper.getBlockTracker((Entity)player));
                break;
            }
            default: {
                extrapolationEntity = null;
                break;
            }
        }
        final BlockPos pos = (extrapolationEntity == null || !extrapolationEntity.active) ? player.getPosition().down() : extrapolationEntity.getPosition().down();
        for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
            final PositionData pData = this.selfCalc(data, pos.offset(facing), entities, friends, obby, blackList);
            if (pData != null) {
                this.checkPlayer(data, player, pData);
            }
        }
    }
    
    private PositionData selfCalc(final PlaceData placeData, final BlockPos pos, final List<Entity> entities, final List<EntityPlayer> friends, final boolean obby, final Set<BlockPos> blackList) {
        if (blackList.contains(pos)) {
            return null;
        }
        final PositionData data = PositionData.create(pos, obby, (this.module.rotate.getValue() != AutoCrystal.ACRotate.None && this.module.rotate.getValue() != AutoCrystal.ACRotate.Break) ? 0 : ((int)this.module.helpingBlocks.getValue()), (boolean)this.module.newVer.getValue(), (boolean)this.module.newVerEntities.getValue(), this.module.getDeathTime(), entities, (boolean)this.module.lava.getValue(), (boolean)this.module.water.getValue(), (boolean)this.module.ignoreLavaItems.getValue(), this.module);
        if (data.isBlocked() && !(boolean)this.module.fallBack.getValue()) {
            return null;
        }
        if (data.isLiquid()) {
            if (!data.isLiquidValid() || ((boolean)this.module.liquidRayTrace.getValue() && (((boolean)this.module.newVer.getValue() && data.getPos().getY() >= Util.mc.player.posY + 2.0) || (!(boolean)this.module.newVer.getValue() && data.getPos().getY() >= Util.mc.player.posY + 1.0))) || BlockUtils.getDistanceSq(pos.up()) >= MathUtil.square((double)(float)this.module.placeRange.getValue()) || BlockUtils.getDistanceSq(pos.up(2)) >= MathUtil.square((double)(float)this.module.placeRange.getValue())) {
                return null;
            }
            if (data.usesObby()) {
                if (data.isObbyValid()) {
                    placeData.getLiquidObby().put(data.getPos(), data);
                }
                return null;
            }
            placeData.getLiquid().add(data);
            return null;
        }
        else {
            if (data.usesObby()) {
                if (data.isObbyValid()) {
                    placeData.getAllObbyData().put(data.getPos(), data);
                }
                return null;
            }
            if (!data.isValid()) {
                return null;
            }
            return this.validate(placeData, data, friends);
        }
    }
    
    public PositionData validate(final PlaceData placeData, final PositionData data, final List<EntityPlayer> friends) {
        if (BlockUtils.getDistanceSq(data.getPos()) >= MathUtil.square((double)(float)this.module.placeTrace.getValue()) && this.noPlaceTrace(data.getPos())) {
            if (!(boolean)this.module.rayTraceBypass.getValue() || !(boolean)this.module.forceBypass.getValue() || data.isLiquid() || data.usesObby()) {
                return null;
            }
            data.setRaytraceBypass(true);
        }
        final float selfDamage = this.module.damageHelper.getDamage(data.getPos());
        if (selfDamage > placeData.getHighestSelfDamage()) {
            placeData.setHighestSelfDamage(selfDamage);
        }
        if (selfDamage > Util.mc.player.getHealth() - 1.0 && !(boolean)this.module.suicide.getValue()) {
            return null;
        }
        if (selfDamage > (float)this.module.maxSelfPlace.getValue() && !(boolean)this.module.override.getValue()) {
            return null;
        }
        if (this.checkFriends(data, friends)) {
            return null;
        }
        data.setSelfDamage(selfDamage);
        return data;
    }
    
    private boolean noPlaceTrace(final BlockPos pos) {
        if (this.module.isNotCheckingRotations() || ((boolean)this.module.rayTraceBypass.getValue() && !Visible.INSTANCE.check(pos, (int)this.module.bypassTicks.getValue()))) {
            return false;
        }
        if (this.module.smartTrace.getValue()) {
            for (final EnumFacing facing : EnumFacing.values()) {
                final Ray ray = RayTraceFactory.rayTrace((Entity)Util.mc.player, pos, facing, (IBlockAccess)Util.mc.world, Blocks.OBSIDIAN.getDefaultState(), (double)this.module.traceWidth.getValue());
                if (ray.isLegit()) {
                    return false;
                }
            }
            return true;
        }
        if (this.module.ignoreNonFull.getValue()) {
            for (final EnumFacing facing : EnumFacing.values()) {
                final Ray ray = RayTraceFactory.rayTrace((Entity)Util.mc.player, pos, facing, (IBlockAccess)Util.mc.world, Blocks.OBSIDIAN.getDefaultState(), (double)this.module.traceWidth.getValue());
                if (!Util.mc.world.getBlockState(ray.getResult().getBlockPos()).getBlock().isFullBlock(Util.mc.world.getBlockState(ray.getResult().getBlockPos()))) {
                    return false;
                }
            }
        }
        return !RayTraceUtil.raytracePlaceCheck((Entity)Util.mc.player, pos);
    }
    
    private void calc(final BlockPos pos, final PlaceData data, final List<EntityPlayer> players, final List<EntityPlayer> friends, final List<Entity> entities, final boolean obby, final Set<BlockPos> blackList, final double maxY) {
        if (this.placeCheck(pos, maxY) || (data.getTarget() != null && data.getTarget().getDistanceSq(pos) > MathUtil.square((double)(float)this.module.range.getValue()))) {
            return;
        }
        final PositionData positionData = this.selfCalc(data, pos, entities, friends, obby, blackList);
        if (positionData == null) {
            return;
        }
        this.calcPositionData(data, positionData, players);
    }
    
    public void calcPositionData(final PlaceData data, final PositionData positionData, final List<EntityPlayer> players) {
        boolean isAntiTotem = false;
        if (data.getTarget() == null) {
            for (final EntityPlayer player : players) {
                isAntiTotem = (this.checkPlayer(data, player, positionData) || isAntiTotem);
            }
        }
        else {
            isAntiTotem = this.checkPlayer(data, data.getTarget(), positionData);
        }
        if (positionData.isRaytraceBypass() && (((boolean)this.module.rayBypassFacePlace.getValue() && positionData.getFacePlacer() != null) || positionData.getMaxDamage() > data.getMinDamage())) {
            data.getRaytraceData().add(positionData);
            return;
        }
        if (positionData.isForce()) {
            final ForcePosition forcePosition = new ForcePosition(positionData, this.module);
            for (final EntityPlayer forced : positionData.getForced()) {
                data.addForceData(forced, forcePosition);
            }
        }
        if (isAntiTotem) {
            data.addAntiTotem(new AntiTotemData(positionData, this.module));
        }
        if (positionData.getFacePlacer() != null || positionData.getMaxDamage() > data.getMinDamage()) {
            data.getData().add(positionData);
        }
        else if ((boolean)this.module.shield.getValue() && !positionData.usesObby() && !positionData.isLiquid() && positionData.isValid() && positionData.getSelfDamage() <= (float)this.module.shieldSelfDamage.getValue()) {
            if (this.module.shieldPrioritizeHealth.getValue()) {
                positionData.setDamage(0.0f);
            }
            positionData.setTarget(data.getShieldPlayer());
            data.getShieldData().add(positionData);
        }
    }
    
    private boolean placeCheck(final BlockPos pos, final double maxY) {
        return pos.getY() < 0 || pos.getY() - 1 >= maxY || BlockUtils.getDistanceSq(pos) >= MathUtil.square((double)(float)this.module.placeRange.getValue()) || (this.module.isOutsideBreakRange(pos, this.module) || this.module.rangeHelper.isCrystalOutsideNegativeRange(pos)) || (distanceSq(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, (Entity)Util.mc.player) > MathUtil.square((double)(float)this.module.pbTrace.getValue()) && !RayTraceUtil.canBeSeen(new Vec3d((double)(pos.getX() + 0.5f), pos.getY() + 1 + 1.7, (double)(pos.getZ() + 0.5f)), (Entity)Util.mc.player));
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
    
    private boolean checkFriends(final PositionData data, final List<EntityPlayer> friends) {
        if (!this.module.shouldCalcFuckinBitch(AutoCrystal.AntiFriendPop.Place)) {
            return false;
        }
        for (final EntityPlayer friend : friends) {
            if (friend != null && !EntityUtil.isDead((Entity)friend) && this.module.damageHelper.getDamage(data.getPos(), (EntityLivingBase)friend) > friend.getHealth() - 0.5f) {
                return true;
            }
        }
        return false;
    }
    
    private boolean checkPlayer(final PlaceData data, final EntityPlayer player, final PositionData positionData) {
        final BlockPos pos = positionData.getPos();
        if (data.getTarget() == null && player.getDistanceSq(pos) > MathUtil.square((double)(float)this.module.range.getValue())) {
            return false;
        }
        boolean result = false;
        final float health = player.getHealth();
        final float damage = this.module.damageHelper.getDamage(pos, (EntityLivingBase)player);
        if ((boolean)this.module.antiTotem.getValue() && !positionData.usesObby() && !positionData.isLiquid() && !positionData.isRaytraceBypass()) {
            if (this.module.antiTotemHelper.isDoublePoppable(player)) {
                if (damage > (float)this.module.popDamage.getValue()) {
                    data.addCorrespondingData(player, positionData);
                }
                else if (damage < health + (float)this.module.maxTotemOffset.getValue() && damage > health + (float)this.module.minTotemOffset.getValue()) {
                    positionData.addAntiTotem(player);
                    result = true;
                }
            }
            else if ((boolean)this.module.forceAntiTotem.getValue() && Thunderhack.combatManager.lastPop((Entity)player) > 500L) {
                if (damage > (float)this.module.popDamage.getValue()) {
                    data.confirmHighDamageForce(player);
                }
                if (damage > 0.0f && damage < (float)this.module.totemHealth.getValue() + (float)this.module.maxTotemOffset.getValue()) {
                    data.confirmPossibleAntiTotem(player);
                }
                final float force = health - damage;
                if (force > 0.0f && force < (float)this.module.totemHealth.getValue()) {
                    positionData.addForcePlayer(player);
                    if (force < positionData.getMinDiff()) {
                        positionData.setMinDiff(force);
                    }
                }
            }
        }
        if (damage > (float)this.module.minFaceDmg.getValue() && (health < (float)this.module.facePlace.getValue() || ((IEntityLivingBase)player).getLowestDurability() <= (float)this.module.armorPlace.getValue())) {
            positionData.setFacePlacer(player);
        }
        if (damage > positionData.getMaxDamage()) {
            positionData.setDamage(damage);
            positionData.setTarget(player);
        }
        return result;
    }
}
