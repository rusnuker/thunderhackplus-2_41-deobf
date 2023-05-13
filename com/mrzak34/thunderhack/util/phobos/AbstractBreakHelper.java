//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import java.util.*;
import net.minecraft.entity.*;

public abstract class AbstractBreakHelper<T extends CrystalData> implements IBreakHelper<T>
{
    protected final AutoCrystal module;
    
    public AbstractBreakHelper(final AutoCrystal module) {
        this.module = module;
    }
    
    protected abstract T newCrystalData(final Entity p0);
    
    protected abstract boolean isValid(final Entity p0, final T p1);
    
    protected abstract boolean calcSelf(final BreakData<T> p0, final Entity p1, final T p2);
    
    protected abstract void calcCrystal(final BreakData<T> p0, final T p1, final Entity p2, final List<EntityPlayer> p3);
    
    @Override
    public BreakData<T> getData(final Collection<T> dataSet, final List<Entity> entities, final List<EntityPlayer> players, final List<EntityPlayer> friends) {
        final BreakData<T> data = this.newData(dataSet);
        for (final Entity crystal : entities) {
            if (crystal instanceof EntityEnderCrystal) {
                if (EntityUtil.isDead(crystal)) {
                    if (!(boolean)this.module.countDeadCrystals.getValue()) {
                        continue;
                    }
                    if (this.module.countDeathTime.getValue()) {
                        if (EntityUtil.isDead(crystal) && Thunderhack.setDeadManager.passedDeathTime(crystal, this.module.getDeathTime())) {
                            continue;
                        }
                        if (((IEntity)crystal).isPseudoDeadT() && ((IEntity)crystal).getPseudoTimeT().passedMs(this.module.getDeathTime())) {
                            continue;
                        }
                    }
                }
                final T crystalData = this.newCrystalData(crystal);
                if (this.calcSelf(data, crystal, crystalData)) {
                    continue;
                }
                if (!this.isValid(crystal, crystalData)) {
                    continue;
                }
                if (this.module.shouldCalcFuckinBitch(AutoCrystal.AntiFriendPop.Break) && this.checkFriendPop(crystal, friends)) {
                    continue;
                }
                this.calcCrystal(data, crystalData, crystal, players);
            }
        }
        return data;
    }
    
    protected boolean checkFriendPop(final Entity entity, final List<EntityPlayer> friends) {
        for (final EntityPlayer friend : friends) {
            final float fDamage = this.module.damageHelper.getDamage(entity, (EntityLivingBase)friend);
            if (fDamage > EntityUtil.getHealth((Entity)friend) - 1.0f) {
                return true;
            }
        }
        return false;
    }
}
