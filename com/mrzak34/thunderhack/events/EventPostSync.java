//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import com.mrzak34.thunderhack.util.phobos.*;

public class EventPostSync extends Event
{
    private final Deque<Runnable> postEvents;
    
    public EventPostSync() {
        this.postEvents = new ArrayDeque<Runnable>();
    }
    
    public void addPostEvent(final SafeRunnable runnable) {
        this.postEvents.add(runnable);
    }
    
    public Deque<Runnable> getPostEvents() {
        return this.postEvents;
    }
}
