//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;

@Cancelable
public class StepEvent extends Event
{
    private final AxisAlignedBB axisAlignedBB;
    private float height;
    
    public StepEvent(final AxisAlignedBB axisAlignedBB, final float height) {
        this.axisAlignedBB = axisAlignedBB;
        this.height = height;
    }
    
    public AxisAlignedBB getAxisAlignedBB() {
        return this.axisAlignedBB;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public void setHeight(final float in) {
        this.height = in;
    }
}
