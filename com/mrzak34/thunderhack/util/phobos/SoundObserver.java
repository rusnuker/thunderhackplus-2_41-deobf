//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.network.play.server.*;
import java.util.function.*;

public abstract class SoundObserver implements Observer<SPacketSoundEffect>
{
    private final BooleanSupplier soundRemove;
    
    public SoundObserver(final BooleanSupplier soundRemove) {
        this.soundRemove = soundRemove;
    }
    
    public boolean shouldRemove() {
        return this.soundRemove.getAsBoolean();
    }
    
    public boolean shouldBeNotified() {
        return this.shouldRemove();
    }
}
