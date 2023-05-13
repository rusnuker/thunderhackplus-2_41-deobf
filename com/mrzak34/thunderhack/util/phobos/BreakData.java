//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.util.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.util.*;

public class BreakData<T extends CrystalData>
{
    private final Collection<T> data;
    private float fallBackDmg;
    private Entity antiTotem;
    private Entity fallBack;
    private int shieldCount;
    
    public BreakData(final Collection<T> data) {
        this.fallBackDmg = Float.MAX_VALUE;
        this.data = data;
    }
    
    public void register(final T dataIn) {
        if (dataIn.getSelfDmg() < this.fallBackDmg && !EntityUtil.isDead(dataIn.getCrystal())) {
            this.fallBack = dataIn.getCrystal();
            this.fallBackDmg = dataIn.getSelfDmg();
        }
        this.data.add(dataIn);
    }
    
    public float getFallBackDmg() {
        return this.fallBackDmg;
    }
    
    public Entity getAntiTotem() {
        return this.antiTotem;
    }
    
    public void setAntiTotem(final Entity antiTotem) {
        this.antiTotem = antiTotem;
    }
    
    public Entity getFallBack() {
        return this.fallBack;
    }
    
    public Collection<T> getData() {
        return this.data;
    }
    
    public int getShieldCount() {
        return this.shieldCount;
    }
    
    public void setShieldCount(final int shieldCount) {
        this.shieldCount = shieldCount;
    }
}
