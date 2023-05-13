//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.util.concurrent.*;

public class ThreadManager implements GlobalExecutor
{
    public Future<?> submit(final SafeRunnable runnable) {
        return this.submitRunnable((Runnable)runnable);
    }
    
    public Future<?> submitRunnable(final Runnable runnable) {
        return ThreadManager.EXECUTOR.submit(runnable);
    }
    
    public void shutDown() {
        ThreadManager.EXECUTOR.shutdown();
    }
}
