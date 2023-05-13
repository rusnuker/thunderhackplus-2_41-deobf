//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;

@Cancelable
public class KeyboardEvent extends Event
{
    private final boolean eventState;
    private final char character;
    private final int key;
    
    public KeyboardEvent(final boolean eventState, final int key, final char character) {
        this.eventState = eventState;
        this.key = key;
        this.character = character;
    }
    
    public boolean getEventState() {
        return this.eventState;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public char getCharacter() {
        return this.character;
    }
    
    public static class Post
    {
    }
}
