//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;

public class ChorusEvent extends Event
{
    private final double chorusX;
    private final double chorusY;
    private final double chorusZ;
    
    public ChorusEvent(final double x, final double y, final double z) {
        this.chorusX = x;
        this.chorusY = y;
        this.chorusZ = z;
    }
    
    public double getChorusX() {
        return this.chorusX;
    }
    
    public double getChorusY() {
        return this.chorusY;
    }
    
    public double getChorusZ() {
        return this.chorusZ;
    }
}
