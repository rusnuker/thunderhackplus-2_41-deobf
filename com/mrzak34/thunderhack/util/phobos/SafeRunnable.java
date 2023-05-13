//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

@FunctionalInterface
public interface SafeRunnable extends Runnable
{
    void runSafely() throws Throwable;
    
    default void run() {
        try {
            this.runSafely();
        }
        catch (Throwable t) {
            this.handle(t);
        }
    }
    
    default void handle(final Throwable t) {
        t.printStackTrace();
    }
}
