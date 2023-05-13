//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.util.concurrent.atomic.*;

public abstract class Finishable implements Runnable
{
    private final AtomicBoolean finished;
    
    public Finishable() {
        this(new AtomicBoolean());
    }
    
    public Finishable(final AtomicBoolean finished) {
        this.finished = finished;
    }
    
    @Override
    public void run() {
        try {
            this.execute();
        }
        finally {
            this.setFinished(true);
        }
    }
    
    protected abstract void execute();
    
    public boolean isFinished() {
        return this.finished.get();
    }
    
    public void setFinished(final boolean finished) {
        this.finished.set(finished);
    }
}
