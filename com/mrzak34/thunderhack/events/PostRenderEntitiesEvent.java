//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;

public class PostRenderEntitiesEvent extends Event
{
    private final float partialTicks;
    private final int pass;
    
    public PostRenderEntitiesEvent(final float partialTicks, final int pass) {
        this.partialTicks = partialTicks;
        this.pass = pass;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    public int getPass() {
        return this.pass;
    }
}
