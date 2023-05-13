//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;

public class EventSprint extends Event
{
    private boolean sprintState;
    
    public EventSprint(final boolean sprintState) {
        this.sprintState = sprintState;
    }
    
    public boolean getSprintState() {
        return this.sprintState;
    }
    
    public void setSprintState(final boolean sprintState) {
        this.sprintState = sprintState;
    }
}
