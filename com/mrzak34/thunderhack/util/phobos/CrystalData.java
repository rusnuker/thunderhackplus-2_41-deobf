//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.entity.*;

public class CrystalData implements Comparable<CrystalData>
{
    private final Entity crystal;
    private float selfDmg;
    private float damage;
    private float[] rotations;
    private double angle;
    
    public CrystalData(final Entity crystal) {
        this.crystal = crystal;
    }
    
    public Entity getCrystal() {
        return this.crystal;
    }
    
    public float getSelfDmg() {
        return this.selfDmg;
    }
    
    public void setSelfDmg(final float damage) {
        this.selfDmg = damage;
    }
    
    public float getDamage() {
        return this.damage;
    }
    
    public void setDamage(final float damage) {
        this.damage = damage;
    }
    
    public float[] getRotations() {
        return this.rotations;
    }
    
    public double getAngle() {
        return this.angle;
    }
    
    public boolean hasCachedRotations() {
        return this.rotations != null;
    }
    
    public void cacheRotations(final float[] rotations, final double angle) {
        this.rotations = rotations;
        this.angle = angle;
    }
    
    @Override
    public int compareTo(final CrystalData o) {
        if (Math.abs(o.damage - this.damage) < 1.0f) {
            return Float.compare(this.selfDmg, o.selfDmg);
        }
        return Float.compare(o.damage, this.damage);
    }
    
    @Override
    public int hashCode() {
        return this.crystal.getPosition().hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof CrystalData && this.hashCode() == o.hashCode();
    }
}
