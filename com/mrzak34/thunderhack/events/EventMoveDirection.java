//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;

@Cancelable
public class EventMoveDirection extends Event
{
    private boolean post;
    
    public EventMoveDirection(final boolean post) {
        this.post = post;
    }
    
    public boolean isPost() {
        return this.post;
    }
}
