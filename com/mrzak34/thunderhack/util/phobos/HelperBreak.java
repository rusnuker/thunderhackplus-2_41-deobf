//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.util.math.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.util.*;

public class HelperBreak extends AbstractBreakHelper<CrystalData>
{
    public HelperBreak(final AutoCrystal module) {
        super(module);
    }
    
    public BreakData<CrystalData> newData(final Collection<CrystalData> data) {
        return (BreakData<CrystalData>)new BreakData((Collection)data);
    }
    
    protected CrystalData newCrystalData(final Entity crystal) {
        return new CrystalData(crystal);
    }
    
    protected boolean isValid(final Entity crystal, final CrystalData data) {
        final double distance = Thunderhack.positionManager.getDistanceSq(crystal);
        return (distance <= MathUtil.square((double)(float)this.module.breakTrace.getValue()) || Thunderhack.positionManager.canEntityBeSeen(crystal)) && this.module.rangeHelper.isCrystalInRangeOfLastPosition(crystal);
    }
    
    protected boolean calcSelf(final BreakData<CrystalData> breakData, final Entity crystal, final CrystalData data) {
        final float selfDamage = this.module.damageHelper.getDamage(crystal);
        data.setSelfDmg(selfDamage);
        if (selfDamage <= (float)this.module.shieldSelfDamage.getValue()) {
            breakData.setShieldCount(breakData.getShieldCount() + 1);
        }
        return selfDamage > EntityUtil.getHealth((Entity)Util.mc.player) - 1.0f && !(boolean)this.module.suicide.getValue();
    }
    
    protected void calcCrystal(final BreakData<CrystalData> data, final CrystalData crystalData, final Entity crystal, final List<EntityPlayer> players) {
        boolean highSelf = crystalData.getSelfDmg() > (float)this.module.maxSelfBreak.getValue();
        if (!(boolean)this.module.suicide.getValue() && !(boolean)this.module.overrideBreak.getValue() && highSelf) {
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
                killing = true;
                highSelf = false;
            }
            if (playerDamage <= damage) {
                continue;
            }
            damage = playerDamage;
        }
        if ((boolean)this.module.antiTotem.getValue() && !EntityUtil.isDead(crystal) && crystal.getPosition().down().equals((Object)this.module.antiTotemHelper.getTargetPos())) {
            data.setAntiTotem(crystal);
        }
        if (!highSelf && (!(boolean)this.module.efficient.getValue() || damage > crystalData.getSelfDmg() || killing)) {
            data.register(crystalData);
        }
    }
}
