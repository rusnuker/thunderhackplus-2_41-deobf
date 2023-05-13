//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.gui.*;

public class Render2DEvent extends Event
{
    public float partialTicks;
    public ScaledResolution scaledResolution;
    
    public Render2DEvent(final float partialTicks, final ScaledResolution scaledResolution) {
        this.partialTicks = partialTicks;
        this.scaledResolution = scaledResolution;
    }
    
    public void setScaledResolution(final ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
    }
    
    public double getScreenWidth() {
        return this.scaledResolution.getScaledWidth_double();
    }
    
    public double getScreenHeight() {
        return this.scaledResolution.getScaledHeight_double();
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    public void setPartialTicks(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
