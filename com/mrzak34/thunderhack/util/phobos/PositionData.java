//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.item.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import com.mrzak34.thunderhack.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;

public class PositionData extends BasePath implements Util, Comparable<PositionData>
{
    private final AutoCrystal module;
    private final List<EntityPlayer> forced;
    private final Set<EntityPlayer> antiTotems;
    private EntityPlayer target;
    private EntityPlayer facePlace;
    private IBlockState state;
    private float selfDamage;
    private float damage;
    private boolean obby;
    private boolean obbyValid;
    private boolean blocked;
    private boolean liquidValid;
    private boolean liquid;
    private float minDiff;
    private boolean raytraceBypass;
    
    public PositionData(final BlockPos pos, final int blocks, final AutoCrystal module) {
        this(pos, blocks, module, new HashSet<EntityPlayer>());
    }
    
    public PositionData(final BlockPos pos, final int blocks, final AutoCrystal module, final Set<EntityPlayer> antiTotems) {
        super((Entity)PositionData.mc.player, pos, blocks);
        this.forced = new ArrayList<EntityPlayer>();
        this.module = module;
        this.antiTotems = antiTotems;
        this.minDiff = Float.MAX_VALUE;
    }
    
    public static PositionData create(final BlockPos pos, final boolean obby, final int helpingBlocks, final boolean newVer, final boolean newVerEntities, final int deathTime, final List<Entity> entities, final boolean lava, final boolean water, final boolean lavaItems, final AutoCrystal module) {
        final PositionData data = new PositionData(pos, helpingBlocks, module);
        data.state = PositionData.mc.world.getBlockState(pos);
        if (data.state.getBlock() != Blocks.BEDROCK && data.state.getBlock() != Blocks.OBSIDIAN) {
            if (!obby || !data.state.getMaterial().isReplaceable() || checkEntities(data, pos, entities, 0, true, true, false)) {
                return data;
            }
            data.obby = true;
        }
        final BlockPos up = pos.up();
        final IBlockState upState = PositionData.mc.world.getBlockState(up);
        if (upState.getBlock() != Blocks.AIR) {
            if (!checkLiquid(upState.getBlock(), water, lava)) {
                return data;
            }
            data.liquid = true;
        }
        final IBlockState upUpState;
        if (!newVer && (upUpState = PositionData.mc.world.getBlockState(up.up())).getBlock() != Blocks.AIR) {
            if (!checkLiquid(upUpState.getBlock(), water, lava)) {
                return data;
            }
            data.liquid = true;
        }
        final boolean checkLavaItems = lavaItems && upState.getMaterial() == Material.LAVA;
        if (checkEntities(data, up, entities, deathTime, false, false, checkLavaItems) || (!newVerEntities && checkEntities(data, up.up(), entities, deathTime, false, false, checkLavaItems))) {
            return data;
        }
        if (data.obby) {
            if (data.liquid) {
                data.liquidValid = true;
            }
            data.obbyValid = true;
            return data;
        }
        if (data.liquid) {
            data.liquidValid = true;
            return data;
        }
        data.setValid(true);
        return data;
    }
    
    private static boolean checkEntities(final PositionData data, final BlockPos pos, final List<Entity> entities, final int deathTime, final boolean dead, final boolean spawning, final boolean lavaItems) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos);
        for (final Entity entity : entities) {
            if (entity != null && (!spawning || entity.preventEntitySpawning) && (!dead || !EntityUtil.isDead(entity))) {
                if (!data.module.bbBlockingHelper.blocksBlock(bb, entity)) {
                    continue;
                }
                if (lavaItems && entity instanceof EntityItem) {
                    continue;
                }
                if (!(entity instanceof EntityEnderCrystal)) {
                    return true;
                }
                if (!dead) {
                    final boolean crystalIsDead = entity.isDead;
                    final boolean crystalIsPseudoDead = ((IEntity)entity).isPseudoDeadT();
                    if (crystalIsDead || crystalIsPseudoDead) {
                        if (crystalIsDead && Thunderhack.setDeadManager.passedDeathTime(entity, deathTime)) {
                            continue;
                        }
                        if (crystalIsPseudoDead && ((IEntity)entity).getPseudoTimeT().passedMs(deathTime)) {
                            continue;
                        }
                        return true;
                    }
                    else {
                        data.blocked = true;
                    }
                }
                data.getBlockingEntities().add(new BlockingEntity(entity, pos));
            }
        }
        return false;
    }
    
    private static boolean checkLiquid(final Block block, final boolean water, final boolean lava) {
        return (water && (block == Blocks.WATER || block == Blocks.FLOWING_WATER)) || (lava && (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA));
    }
    
    public boolean usesObby() {
        return this.obby;
    }
    
    public boolean isObbyValid() {
        return this.obbyValid;
    }
    
    public float getMaxDamage() {
        return this.damage;
    }
    
    public void setDamage(final float damage) {
        this.damage = damage;
    }
    
    public float getSelfDamage() {
        return this.selfDamage;
    }
    
    public void setSelfDamage(final float selfDamage) {
        this.selfDamage = selfDamage;
    }
    
    public EntityPlayer getTarget() {
        return this.target;
    }
    
    public void setTarget(final EntityPlayer target) {
        this.target = target;
    }
    
    public EntityPlayer getFacePlacer() {
        return this.facePlace;
    }
    
    public void setFacePlacer(final EntityPlayer facePlace) {
        this.facePlace = facePlace;
    }
    
    public Set<EntityPlayer> getAntiTotems() {
        return this.antiTotems;
    }
    
    public void addAntiTotem(final EntityPlayer player) {
        this.antiTotems.add(player);
    }
    
    public boolean isBlocked() {
        return this.blocked;
    }
    
    public float getMinDiff() {
        return this.minDiff;
    }
    
    public void setMinDiff(final float minDiff) {
        this.minDiff = minDiff;
    }
    
    public boolean isForce() {
        return !this.forced.isEmpty();
    }
    
    public void addForcePlayer(final EntityPlayer player) {
        this.forced.add(player);
    }
    
    public List<EntityPlayer> getForced() {
        return this.forced;
    }
    
    public boolean isLiquidValid() {
        return this.liquidValid;
    }
    
    public boolean isLiquid() {
        return this.liquid;
    }
    
    public float getHealth() {
        final EntityLivingBase target = (EntityLivingBase)this.getTarget();
        return (target == null) ? 36.0f : EntityUtil.getHealth((Entity)target);
    }
    
    public int compareTo(final PositionData o) {
        if (this.module.useSafetyFactor.getValue()) {
            final double thisFactor = this.damage * (double)this.module.safetyFactor.getValue() - this.selfDamage * (double)this.module.selfFactor.getValue();
            final double otherFactor = o.damage * (double)this.module.safetyFactor.getValue() - o.selfDamage * (double)this.module.selfFactor.getValue();
            if (thisFactor != otherFactor) {
                return Double.compare(otherFactor, thisFactor);
            }
        }
        if (Math.abs(o.damage - this.damage) >= (double)this.module.compareDiff.getValue() || ((boolean)this.module.facePlaceCompare.getValue() && this.damage < (float)this.module.minDamage.getValue())) {
            return Float.compare(o.damage, this.damage);
        }
        if (this.usesObby() && o.usesObby()) {
            return Integer.compare(this.getPath().length, o.getPath().length) + Float.compare(this.selfDamage, o.selfDamage);
        }
        return Float.compare(this.selfDamage, o.getSelfDamage());
    }
    
    public boolean equals(final Object o) {
        return o instanceof PositionData && ((PositionData)o).getPos().equals((Object)this.getPos());
    }
    
    public int hashCode() {
        return this.getPos().hashCode();
    }
    
    public boolean isRaytraceBypass() {
        return this.raytraceBypass;
    }
    
    public void setRaytraceBypass(final boolean raytraceBypass) {
        this.raytraceBypass = raytraceBypass;
    }
}
