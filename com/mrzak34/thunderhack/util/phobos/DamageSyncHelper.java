//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.*;

public class DamageSyncHelper
{
    private final DiscreteTimer discreteTimer;
    private final Timer timer;
    private final Setting<Integer> syncDelay;
    private final Setting<Boolean> discrete;
    private final Setting<Boolean> danger;
    private final Confirmer confirmer;
    private float lastDamage;
    
    public DamageSyncHelper(final Setting<Boolean> discrete, final Setting<Integer> syncDelay, final Setting<Boolean> danger) {
        this.discreteTimer = new GuardTimer(1000L, 5L);
        this.timer = new Timer();
        this.danger = danger;
        this.confirmer = Confirmer.createAndSubscribe();
        this.syncDelay = syncDelay;
        this.discrete = discrete;
        this.discreteTimer.reset((int)syncDelay.getValue());
    }
    
    public void setSync(final BlockPos pos, final float damage, final boolean newVer) {
        final int placeTime = (int)(Thunderhack.serverManager.getPing() / 2.0 + 1.0);
        this.confirmer.setPos(pos.toImmutable(), newVer, placeTime);
        this.lastDamage = damage;
        if ((boolean)this.discrete.getValue() && this.discreteTimer.passed((int)this.syncDelay.getValue())) {
            this.discreteTimer.reset((int)this.syncDelay.getValue());
        }
        else if (!(boolean)this.discrete.getValue() && this.timer.passed((int)this.syncDelay.getValue())) {
            this.timer.reset();
        }
    }
    
    public boolean isSyncing(final float damage, final boolean damageSync) {
        return damageSync && !(boolean)this.danger.getValue() && this.confirmer.isValid() && damage <= this.lastDamage && (((boolean)this.discrete.getValue() && !this.discreteTimer.passed((int)this.syncDelay.getValue())) || (!(boolean)this.discrete.getValue() && !this.timer.passed((int)this.syncDelay.getValue())));
    }
    
    public Confirmer getConfirmer() {
        return this.confirmer;
    }
}
