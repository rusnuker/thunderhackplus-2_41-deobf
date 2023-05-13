//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.*;

public class ThreadFactoryBuilder
{
    private Boolean daemon;
    private String nameFormat;
    
    private static String format(final String format, final Object... args) {
        return String.format(Locale.ROOT, format, args);
    }
    
    public ThreadFactoryBuilder setDaemon(final boolean daemon) {
        this.daemon = daemon;
        return this;
    }
    
    public ThreadFactoryBuilder setNameFormat(final String nameFormat) {
        this.nameFormat = nameFormat;
        return this;
    }
    
    public ThreadFactory build() {
        final Boolean daemon = this.daemon;
        final String nameFormat = this.nameFormat;
        final AtomicLong id = (nameFormat != null) ? new AtomicLong(0L) : null;
        final Thread thread;
        final Boolean b;
        final String format;
        final AtomicLong atomicLong;
        return r -> {
            thread = Executors.defaultThreadFactory().newThread(r);
            if (b != null) {
                thread.setDaemon(b);
            }
            if (format != null) {
                thread.setName(format(format, atomicLong.getAndIncrement()));
            }
            return thread;
        };
    }
}
