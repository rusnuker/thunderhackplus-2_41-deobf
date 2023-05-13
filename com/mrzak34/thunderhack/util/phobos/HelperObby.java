//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.util.math.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.block.state.*;

public class HelperObby
{
    private final AutoCrystal module;
    float MD;
    
    public HelperObby(final AutoCrystal module) {
        this.MD = 4.0f;
        this.module = module;
    }
    
    public PositionData findBestObbyData(final Map<BlockPos, PositionData> obbyData, final List<EntityPlayer> players, final List<EntityPlayer> friends, final List<Entity> entities, final EntityPlayer target, final boolean newVer) {
        double maxY = 0.0;
        final List<EntityPlayer> filteredPlayers = new LinkedList<EntityPlayer>();
        for (final EntityPlayer player : players) {
            if (player != null && !EntityUtil.isDead((Entity)player) && player.posY <= Util.mc.player.posY + 18.0) {
                if (player.getDistanceSq((Entity)Util.mc.player) > MathUtil.square((double)(float)this.module.targetRange.getValue())) {
                    continue;
                }
                filteredPlayers.add(player);
                if (player.posY <= maxY) {
                    continue;
                }
                maxY = player.posY;
            }
        }
        final int fastObby = (int)this.module.fastObby.getValue();
        if (fastObby != 0) {
            Set<BlockPos> positions;
            if (target != null) {
                positions = new HashSet<BlockPos>((int)(4 * fastObby / 0.75) + 1);
                this.addPositions(positions, target, fastObby);
            }
            else {
                positions = new HashSet<BlockPos>((int)(filteredPlayers.size() * 4 * fastObby / 0.75 + 1.0));
                for (final EntityPlayer player2 : filteredPlayers) {
                    this.addPositions(positions, player2, fastObby);
                }
            }
            obbyData.keySet().retainAll(positions);
        }
        int shortest;
        final int maxPath = shortest = (int)this.module.helpingBlocks.getValue();
        float maxDamage = 0.0f;
        float maxSelfDamage = 0.0f;
        PositionData bestData = null;
        for (final PositionData positionData : obbyData.values()) {
            if (positionData.isBlocked()) {
                continue;
            }
            final BlockPos pos = positionData.getPos();
            if (pos.getY() >= maxY) {
                continue;
            }
            float self = Float.MAX_VALUE;
            final boolean preSelf = (boolean)this.module.obbyPreSelf.getValue();
            final IBlockStateHelper helper = (IBlockStateHelper)new BlockStateHelper((Map)new HashMap());
            if (preSelf) {
                helper.addBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
                self = this.module.damageHelper.getObbyDamage(pos, (IBlockAccess)helper);
                if (this.checkSelfDamage(self)) {
                    continue;
                }
                positionData.setSelfDamage(self);
            }
            final BlockPos[] ignore = new BlockPos[newVer ? 1 : 2];
            ignore[0] = pos.up();
            if (!newVer) {
                ignore[1] = pos.up(2);
            }
            if (this.module.interact.getValue()) {
                final AutoCrystal.RayTraceMode mode = (AutoCrystal.RayTraceMode)this.module.obbyTrace.getValue();
                for (final EnumFacing facing : EnumFacing.values()) {
                    final BlockPos offset = pos.offset(facing);
                    if (BlockUtils.getDistanceSq(offset) < MathUtil.square((double)(float)this.module.placeRange.getValue())) {
                        final IBlockState state = Util.mc.world.getBlockState(offset);
                        if (!state.getMaterial().isReplaceable() || state.getMaterial().isLiquid()) {
                            Ray ray = RayTraceFactory.rayTrace(positionData.getFrom(), offset, facing.getOpposite(), (IBlockAccess)Util.mc.world, Blocks.OBSIDIAN.getDefaultState(), (mode == AutoCrystal.RayTraceMode.Smart) ? -1.0 : 2.0);
                            if (ray.isLegit() || mode != AutoCrystal.RayTraceMode.Smart) {
                                if ((boolean)this.module.inside.getValue() && state.getMaterial().isLiquid()) {
                                    ray.getResult().sideHit = ray.getResult().sideHit.getOpposite();
                                    ray = new Ray(ray.getResult(), ray.getRotations(), ray.getPos().offset(ray.getFacing()), ray.getFacing().getOpposite(), ray.getVector());
                                }
                                positionData.setValid(true);
                                positionData.setPath(new Ray[] { ray });
                                break;
                            }
                        }
                    }
                }
            }
            if (!positionData.isValid()) {
                PathFinder.findPath((Pathable)positionData, (float)this.module.placeRange.getValue(), entities, (AutoCrystal.RayTraceMode)this.module.obbyTrace.getValue(), helper, Blocks.OBSIDIAN.getDefaultState(), PathFinder.CHECK, ignore);
            }
            if (!positionData.isValid() || positionData.getPath() == null) {
                continue;
            }
            if (positionData.getPath().length > maxPath) {
                continue;
            }
            for (final Ray ray2 : positionData.getPath()) {
                helper.addBlockState(ray2.getPos().offset(ray2.getFacing()), Blocks.OBSIDIAN.getDefaultState());
            }
            if (!preSelf) {
                self = this.module.damageHelper.getObbyDamage(pos, (IBlockAccess)helper);
                if (this.checkSelfDamage(self)) {
                    continue;
                }
                positionData.setSelfDamage(self);
            }
            if (this.module.shouldCalcFuckinBitch(AutoCrystal.AntiFriendPop.Place)) {
                boolean poppingFriend = false;
                for (final EntityPlayer friend : friends) {
                    final float damage = this.module.damageHelper.getObbyDamage(pos, (EntityLivingBase)friend, (IBlockAccess)helper);
                    if (damage > EntityUtil.getHealth((Entity)friend)) {
                        poppingFriend = true;
                        break;
                    }
                }
                if (poppingFriend) {
                    continue;
                }
            }
            float damage2 = 0.0f;
            if (target != null) {
                positionData.setTarget(target);
                damage2 = this.module.damageHelper.getObbyDamage(pos, (EntityLivingBase)target, (IBlockAccess)helper);
                if (damage2 < (float)this.module.minDamage.getValue()) {
                    continue;
                }
            }
            else {
                for (final EntityPlayer p : filteredPlayers) {
                    final float d = this.module.damageHelper.getObbyDamage(pos, (EntityLivingBase)p, (IBlockAccess)helper);
                    if (d >= (float)this.module.minDamage.getValue()) {
                        if (d < damage2) {
                            continue;
                        }
                        damage2 = d;
                        positionData.setTarget(p);
                    }
                }
            }
            if (damage2 < (float)this.module.minDamage.getValue()) {
                continue;
            }
            positionData.setDamage(damage2);
            final int length = positionData.getPath().length;
            if (bestData == null) {
                bestData = positionData;
                maxDamage = damage2;
                maxSelfDamage = self;
                shortest = length;
            }
            else {
                final boolean betterLen = length - (int)this.module.maxDiff.getValue() < shortest;
                final boolean betterDmg = damage2 + (double)this.module.maxDmgDiff.getValue() > maxDamage && damage2 - (double)this.module.maxDmgDiff.getValue() >= (float)this.module.minDamage.getValue();
                if ((betterLen || damage2 <= maxDamage) && (betterDmg || length >= shortest) && (!betterDmg || length != shortest || self >= maxSelfDamage)) {
                    continue;
                }
                bestData = positionData;
                if (length < shortest) {
                    shortest = length;
                }
                if (damage2 > maxDamage) {
                    maxDamage = damage2;
                }
                if (self >= maxSelfDamage) {
                    continue;
                }
                maxSelfDamage = self;
            }
        }
        return bestData;
    }
    
    private void addPositions(final Set<BlockPos> positions, final EntityPlayer player, final int fastObby) {
        final BlockPos down = player.getPosition().down();
        for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
            BlockPos offset = down;
            for (int i = 0; i < fastObby; ++i) {
                offset = offset.offset(facing);
                positions.add(offset);
            }
        }
    }
    
    private boolean checkSelfDamage(final float self) {
        if (self > EntityUtil.getHealth((Entity)Util.mc.player) - 1.0) {
            if (this.module.obbySafety.getValue()) {}
            return true;
        }
        return self > (float)this.module.maxSelfPlace.getValue();
    }
}
