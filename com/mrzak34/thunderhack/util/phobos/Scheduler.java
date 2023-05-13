//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.util.*;
import net.minecraftforge.common.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.*;
import java.util.function.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Scheduler
{
    private static final Scheduler INSTANCE;
    private final Queue<Runnable> scheduled;
    private final Queue<Runnable> toSchedule;
    private boolean executing;
    private int gameLoop;
    
    public Scheduler() {
        this.scheduled = new LinkedList<Runnable>();
        this.toSchedule = new LinkedList<Runnable>();
    }
    
    public static Scheduler getInstance() {
        return Scheduler.INSTANCE;
    }
    
    public void init() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void unload() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent
    public void onGameZaloop(final GameZaloopEvent e) {
        this.gameLoop = ((InterfaceMinecraft)Util.mc).getGameLoop();
        this.executing = true;
        CollectionUtil.emptyQueue((Queue)this.scheduled, Runnable::run);
        this.executing = false;
        CollectionUtil.emptyQueue((Queue)this.toSchedule, (Consumer)this.scheduled::add);
    }
    
    public void scheduleAsynchronously(final Runnable runnable) {
        Util.mc.addScheduledTask(() -> this.schedule(runnable, false));
    }
    
    public void schedule(final Runnable runnable, final boolean checkGameLoop) {
        if (Util.mc.isCallingFromMinecraftThread()) {
            if (this.executing || (checkGameLoop && this.gameLoop != ((InterfaceMinecraft)Util.mc).getGameLoop())) {
                this.toSchedule.add(runnable);
            }
            else {
                this.scheduled.add(runnable);
            }
        }
        else {
            Util.mc.addScheduledTask(runnable);
        }
    }
    
    static {
        INSTANCE = new Scheduler();
    }
}
