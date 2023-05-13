//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.util.math.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import java.util.*;

public class HelperBreakMotion extends AbstractBreakHelper<CrystalDataMotion>
{
    public HelperBreakMotion(final AutoCrystal module) {
        super(module);
    }
    
    public BreakData<CrystalDataMotion> newData(final Collection<CrystalDataMotion> data) {
        return (BreakData<CrystalDataMotion>)new BreakData((Collection)data);
    }
    
    protected CrystalDataMotion newCrystalData(final Entity crystal) {
        return new CrystalDataMotion(crystal);
    }
    
    protected boolean isValid(final Entity crystal, final CrystalDataMotion data) {
        double distance = Thunderhack.positionManager.getDistanceSq(crystal);
        if (!this.module.rangeHelper.isCrystalInRangeOfLastPosition(crystal) || (distance >= MathUtil.square((double)(float)this.module.breakTrace.getValue()) && !Thunderhack.positionManager.canEntityBeSeen(crystal))) {
            data.invalidateTiming(CrystalDataMotion.Timing.PRE);
        }
        final EntityPlayer e = (EntityPlayer)Util.mc.player;
        distance = e.getDistanceSq(crystal);
        if (!this.module.rangeHelper.isCrystalInRange(crystal) || (distance >= MathUtil.square((double)(float)this.module.breakTrace.getValue()) && !e.canEntityBeSeen(crystal))) {
            data.invalidateTiming(CrystalDataMotion.Timing.POST);
        }
        return data.getTiming() != CrystalDataMotion.Timing.NONE;
    }
    
    protected boolean calcSelf(final BreakData<CrystalDataMotion> breakData, final Entity crystal, final CrystalDataMotion data) {
        boolean breakCase = true;
        boolean incrementedCount = false;
        switch (data.getTiming()) {
            case BOTH: {
                breakCase = false;
            }
            case PRE: {
                final float preDamage = this.module.damageHelper.getDamage(crystal);
                if (preDamage <= (float)this.module.shieldSelfDamage.getValue()) {
                    breakData.setShieldCount(breakData.getShieldCount() + 1);
                    incrementedCount = true;
                }
                data.setSelfDmg(preDamage);
                if (preDamage > EntityUtil.getHealth((Entity)Util.mc.player) - 1.0f && !(boolean)this.module.suicide.getValue()) {
                    data.invalidateTiming(CrystalDataMotion.Timing.PRE);
                }
                if (breakCase) {
                    break;
                }
            }
            case POST: {
                final float postDamage = this.module.damageHelper.getDamage(crystal, Util.mc.player.getEntityBoundingBox());
                if (!incrementedCount && postDamage <= (float)this.module.shieldSelfDamage.getValue()) {
                    breakData.setShieldCount(breakData.getShieldCount() + 1);
                }
                data.setPostSelf(postDamage);
                if (postDamage > EntityUtil.getHealth((Entity)Util.mc.player) - 1.0f && !(boolean)this.module.suicide.getValue()) {
                    data.invalidateTiming(CrystalDataMotion.Timing.POST);
                    break;
                }
                break;
            }
        }
        return data.getTiming() == CrystalDataMotion.Timing.NONE;
    }
    
    protected void calcCrystal(final BreakData<CrystalDataMotion> data, final CrystalDataMotion crystalData, final Entity crystal, final List<EntityPlayer> players) {
        boolean highPreSelf = crystalData.getSelfDmg() > (float)this.module.maxSelfBreak.getValue();
        boolean highPostSelf = crystalData.getPostSelf() > (float)this.module.maxSelfBreak.getValue();
        if (!(boolean)this.module.suicide.getValue() && !(boolean)this.module.overrideBreak.getValue() && highPreSelf && highPostSelf) {
            crystalData.invalidateTiming(CrystalDataMotion.Timing.PRE);
            crystalData.invalidateTiming(CrystalDataMotion.Timing.POST);
            return;
        }
        float damage = 0.0f;
        boolean killing = false;
        for (final EntityPlayer player : players) {
            if (player.getDistanceSq(crystal) > 144.0) {
                continue;
            }
            final float playerDamage = this.module.damageHelper.getDamage(crystal, (EntityLivingBase)player);
            if (playerDamage > crystalData.getDamage()) {
                crystalData.setDamage(playerDamage);
            }
            if (playerDamage > EntityUtil.getHealth((Entity)player) + 1.0f) {
                highPreSelf = false;
                highPostSelf = false;
                killing = true;
            }
            if (playerDamage <= damage) {
                continue;
            }
            damage = playerDamage;
        }
        if ((boolean)this.module.antiTotem.getValue() && !EntityUtil.isDead(crystal) && crystal.getPosition().down().equals((Object)this.module.antiTotemHelper.getTargetPos())) {
            data.setAntiTotem(crystal);
        }
        if (highPreSelf) {
            crystalData.invalidateTiming(CrystalDataMotion.Timing.PRE);
        }
        if (highPostSelf) {
            crystalData.invalidateTiming(CrystalDataMotion.Timing.POST);
        }
        if ((crystalData.getTiming() != CrystalDataMotion.Timing.NONE && (!(boolean)this.module.efficient.getValue() || damage > crystalData.getSelfDmg())) || killing) {
            data.register((CrystalData)crystalData);
        }
    }
}
