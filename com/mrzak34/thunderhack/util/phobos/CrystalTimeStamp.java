//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

public class CrystalTimeStamp extends TimeStamp
{
    private final float damage;
    private final boolean shield;
    
    public CrystalTimeStamp(final float damage, final boolean shield) {
        this.damage = damage;
        this.shield = shield;
    }
    
    public float getDamage() {
        return this.damage;
    }
    
    public boolean isShield() {
        return this.shield;
    }
}
