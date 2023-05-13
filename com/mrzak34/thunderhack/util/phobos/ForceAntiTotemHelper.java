//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.setting.*;
import net.minecraft.util.math.*;

public class ForceAntiTotemHelper
{
    private final DamageSyncHelper damageSyncHelper;
    private final Setting<Integer> placeConfirm;
    private final Setting<Integer> breakConfirm;
    private BlockPos pos;
    
    public ForceAntiTotemHelper(final Setting<Boolean> discrete, final Setting<Integer> syncDelay, final Setting<Integer> placeConfirm, final Setting<Integer> breakConfirm, final Setting<Boolean> dangerForce) {
        this.damageSyncHelper = new DamageSyncHelper((Setting)discrete, (Setting)syncDelay, (Setting)dangerForce);
        this.placeConfirm = placeConfirm;
        this.breakConfirm = breakConfirm;
    }
    
    public void setSync(final BlockPos pos, final boolean newVer) {
        this.damageSyncHelper.setSync(pos, Float.MAX_VALUE, newVer);
        this.pos = pos;
    }
    
    public boolean isForcing(final boolean damageSync) {
        final Confirmer c = this.damageSyncHelper.getConfirmer();
        if (c.isValid() && (!c.isPlaceConfirmed((int)this.placeConfirm.getValue()) || !c.isBreakConfirmed((int)this.breakConfirm.getValue()))) {
            return c.isValid();
        }
        return this.damageSyncHelper.isSyncing(0.0f, damageSync);
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
}
