//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.util.concurrent.*;

public interface GlobalExecutor
{
    public static final ExecutorService EXECUTOR = ThreadUtil.newDaemonCachedThreadPool();
}
