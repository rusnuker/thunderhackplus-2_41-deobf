//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

public class MineSlots
{
    private final int blockSlot;
    private final int toolSlot;
    private final float damage;
    
    public MineSlots(final int blockSlot, final int toolSlot, final float damage) {
        this.blockSlot = blockSlot;
        this.toolSlot = toolSlot;
        this.damage = damage;
    }
    
    public int getBlockSlot() {
        return this.blockSlot;
    }
    
    public int getToolSlot() {
        return this.toolSlot;
    }
    
    public float getDamage() {
        return this.damage;
    }
}
