//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.util.concurrent.*;

public class ThreadUtil
{
    public static final ThreadFactory FACTORY;
    public static ExecutorService executor;
    
    public static ScheduledExecutorService newDaemonScheduledExecutor(final String name) {
        final ThreadFactoryBuilder factory = newDaemonThreadFactoryBuilder();
        factory.setNameFormat("ThunderHack-" + name + "-%d");
        return Executors.newSingleThreadScheduledExecutor(factory.build());
    }
    
    public static ExecutorService newDaemonCachedThreadPool() {
        return Executors.newCachedThreadPool(ThreadUtil.FACTORY);
    }
    
    public static ExecutorService newFixedThreadPool(final int size) {
        final ThreadFactoryBuilder factory = newDaemonThreadFactoryBuilder();
        factory.setNameFormat("ThunderHack-Fixed-%d");
        return Executors.newFixedThreadPool(Math.max(size, 1), factory.build());
    }
    
    public static ThreadFactoryBuilder newDaemonThreadFactoryBuilder() {
        final ThreadFactoryBuilder factory = new ThreadFactoryBuilder();
        factory.setDaemon(true);
        return factory;
    }
    
    public static void run(final Runnable runnable, final long delay) {
        ThreadUtil.executor.execute(() -> {
            try {
                Thread.sleep(delay);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            runnable.run();
        });
    }
    
    static {
        FACTORY = newDaemonThreadFactoryBuilder().setNameFormat("ThunderHack-Thread-%d").build();
        ThreadUtil.executor = Executors.newCachedThreadPool();
    }
}
