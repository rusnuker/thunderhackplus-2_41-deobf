//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;

public class RenderHand extends Event
{
    private final float ticks;
    
    public RenderHand(final float ticks) {
        this.ticks = ticks;
    }
    
    public float getPartialTicks() {
        return this.ticks;
    }
    
    public static class PostOutline extends RenderHand
    {
        public PostOutline(final float ticks) {
            super(ticks);
        }
    }
    
    public static class PreOutline extends RenderHand
    {
        public PreOutline(final float ticks) {
            super(ticks);
        }
    }
    
    public static class PostFill extends RenderHand
    {
        public PostFill(final float ticks) {
            super(ticks);
        }
    }
    
    public static class PreFill extends RenderHand
    {
        public PreFill(final float ticks) {
            super(ticks);
        }
    }
    
    public static class PostBoth extends RenderHand
    {
        public PostBoth(final float ticks) {
            super(ticks);
        }
    }
    
    public static class PreBoth extends RenderHand
    {
        public PreBoth(final float ticks) {
            super(ticks);
        }
    }
}
