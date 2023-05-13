//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.math.*;

public final class ListenerSound extends SoundObserver
{
    private final AutoCrystal module;
    
    public ListenerSound(final AutoCrystal module) {
        super(module.soundRemove::getValue);
        this.module = module;
    }
    
    @Override
    public void onChange(final SPacketSoundEffect value) {
        if (this.module.soundThread.getValue()) {
            this.module.threadHelper.startThread(new BlockPos[0]);
        }
    }
    
    @Override
    public boolean shouldBeNotified() {
        return true;
    }
}
