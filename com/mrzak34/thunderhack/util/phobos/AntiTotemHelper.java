//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.entity.*;

public class AntiTotemHelper
{
    private final Setting<Float> health;
    private EntityPlayer target;
    private BlockPos targetPos;
    
    public AntiTotemHelper(final Setting<Float> health) {
        this.health = health;
    }
    
    public boolean isDoublePoppable(final EntityPlayer player) {
        return Thunderhack.combatManager.lastPop((Entity)player) > 500L && player.getHealth() <= (float)this.health.getValue();
    }
    
    public BlockPos getTargetPos() {
        return this.targetPos;
    }
    
    public void setTargetPos(final BlockPos targetPos) {
        this.targetPos = targetPos;
    }
    
    public EntityPlayer getTarget() {
        return this.target;
    }
    
    public void setTarget(final EntityPlayer target) {
        this.target = target;
    }
}
