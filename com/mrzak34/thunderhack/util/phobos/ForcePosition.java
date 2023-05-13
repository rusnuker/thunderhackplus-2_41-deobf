//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class ForcePosition extends PositionData
{
    private final PositionData data;
    
    public ForcePosition(final PositionData data, final AutoCrystal module) {
        super(data.getPos(), data.getMaxLength(), module);
        this.data = data;
    }
    
    public PositionData getData() {
        return this.data;
    }
    
    @Override
    public boolean usesObby() {
        return this.data.usesObby();
    }
    
    @Override
    public float getMaxDamage() {
        return this.data.getMaxDamage();
    }
    
    @Override
    public void setDamage(final float damage) {
        this.data.setDamage(damage);
    }
    
    @Override
    public float getSelfDamage() {
        return this.data.getSelfDamage();
    }
    
    @Override
    public void setSelfDamage(final float selfDamage) {
        this.data.setSelfDamage(selfDamage);
    }
    
    @Override
    public EntityPlayer getTarget() {
        return this.data.getTarget();
    }
    
    @Override
    public void setTarget(final EntityPlayer target) {
        this.data.setTarget(target);
    }
    
    @Override
    public EntityPlayer getFacePlacer() {
        return this.data.getFacePlacer();
    }
    
    @Override
    public void setFacePlacer(final EntityPlayer facePlace) {
        this.data.setFacePlacer(facePlace);
    }
    
    @Override
    public Set<EntityPlayer> getAntiTotems() {
        return this.data.getAntiTotems();
    }
    
    @Override
    public void addAntiTotem(final EntityPlayer player) {
        this.data.addAntiTotem(player);
    }
    
    @Override
    public boolean isBlocked() {
        return this.data.isBlocked();
    }
    
    @Override
    public float getMinDiff() {
        return this.data.getMinDiff();
    }
    
    @Override
    public void setMinDiff(final float minDiff) {
        this.data.setMinDiff(minDiff);
    }
    
    @Override
    public boolean isForce() {
        return true;
    }
    
    @Override
    public void addForcePlayer(final EntityPlayer player) {
        this.data.addForcePlayer(player);
    }
    
    @Override
    public boolean isLiquid() {
        return this.data.isLiquid();
    }
    
    @Override
    public int compareTo(final PositionData o) {
        if (o instanceof ForcePosition) {
            final int c = Float.compare(this.getMinDiff(), o.getMinDiff());
            if (c != 0) {
                return c;
            }
        }
        return super.compareTo(o);
    }
}
