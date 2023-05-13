//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;

@Cancelable
public class EventPlayerTravel extends Event
{
    public float Strafe;
    public float Vertical;
    public float Forward;
    
    public float getStrafe() {
        return this.Strafe;
    }
    
    public float getVertical() {
        return this.Vertical;
    }
    
    public float getForward() {
        return this.Forward;
    }
    
    public EventPlayerTravel(final float p_Strafe, final float p_Vertical, final float p_Forward) {
        this.Strafe = p_Strafe;
        this.Vertical = p_Vertical;
        this.Forward = p_Forward;
    }
}
