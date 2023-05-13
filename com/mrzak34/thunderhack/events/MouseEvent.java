//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;

@Cancelable
public class MouseEvent extends Event
{
    private final int button;
    private final boolean state;
    
    public MouseEvent(final int button, final boolean state) {
        this.button = button;
        this.state = state;
    }
    
    public boolean getState() {
        return this.state;
    }
    
    public int getButton() {
        return this.button;
    }
}
