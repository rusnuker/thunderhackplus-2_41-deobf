//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;

@Cancelable
public class PostPlayerUpdateEvent extends Event
{
    private int iterations;
    
    public int getIterations() {
        return this.iterations;
    }
    
    public void setIterations(final int in) {
        this.iterations = in;
    }
}
